package effyis.partners.socle.service.commande;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.transaction.Transactional;

import effyis.partners.socle.dto.commande.FactureDTOREq;
import effyis.partners.socle.enums.Etat;
import effyis.partners.socle.taxeRate.entity.TaxeRate;
import effyis.partners.socle.taxeRate.repository.TaxeRateRepository;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.sun.istack.ByteArrayDataSource;
import effyis.partners.socle.coupon.entity.Coupon;
import effyis.partners.socle.coupon.repository.CouponRepository;
import effyis.partners.socle.dto.MailDTO;
import effyis.partners.socle.dto.commande.FactureDTO;
import effyis.partners.socle.entity.Customer;
import effyis.partners.socle.entity.command.Facture;
import effyis.partners.socle.entity.product.Delivery;
import effyis.partners.socle.entity.product.Product;
import effyis.partners.socle.enums.BucketName;
import effyis.partners.socle.exception.ElementNotFoundException;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.CustomersRepository;
import effyis.partners.socle.repository.commande.FactureRepository;
import effyis.partners.socle.repository.delivery.DelivreyRepository;
import effyis.partners.socle.repository.product.ProductRepository;
import effyis.partners.socle.service.AccountService;
import effyis.partners.socle.service.MailService;
import effyis.partners.socle.service.amazon.FileStoreService;
import effyis.partners.socle.service.implService.StorageService;
import effyis.partners.socle.shippingCost.entity.ShippingCost;
import effyis.partners.socle.shippingCost.repository.ShippingCostRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class FactureServiceImpl implements FactureService {
    @Autowired
    private ModelMapper modelMaper;
    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TaxeRateRepository taxeRateRepository;
    @Autowired
    MailService mailService;
    @Autowired
    AccountService accountService;
    @Autowired
    Cloudinary cloudinary;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    FileStoreService fileStoreService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ShippingCostRepository shippingCostRepository ;
    @Autowired
    StorageService storageService;
    @Value("${custom.mailFrom}")
    private String mailFrom;

    @Transactional
    public void createFacture(FactureDTOREq factureDTO) throws JRException, MessagingException, IOException {
        Coupon coupon = couponRepository.findById(factureDTO.getCouponId()).orElse(null);
        ShippingCost delivery = shippingCostRepository.findById(factureDTO.getDeliveryId()).orElse(null);
        Customer customer = customersRepository.findById(factureDTO.getCustomerId()).orElse(null);
        TaxeRate taxeRate = taxeRateRepository.findById(factureDTO.getTaxRate()).orElse(null);
        List<Product> products = new ArrayList<>();
        factureDTO.getProducts().forEach(p -> {
            Product product = productRepository.findById(p.getId()).orElse(null);
            product.setQuantity(p.getQuantity());
            products.add(product);
        });
        Facture commande = new Facture();
        //modelMaper.map(factureDTO, commande);
        commande.setCoupon(coupon);
        commande.setCustomer(customer);
        commande.setDelivery(delivery);
        commande.setProducts(products);
        commande.setTaxeRate(taxeRate);
        commande.setDescription(factureDTO.getDescription());
        commande.setInvoiceDate(factureDTO.getInvoiceDate());
        commande.setDueDate(factureDTO.getDueDate());
        commande.setEtatCommande(Etat.EnCours);
        commande.setSousTotale(factureDTO.getSousTotale());
        commande.setTotal(factureDTO.getTotal());
        Facture c = factureRepository.save(commande);
        
        exportReport(c.getId());
    }

    //Windows------------------------------------------------------------------------------------------
    @Transactional
    public void exportReport(Long commandeId) throws JRException, MessagingException, IOException {

        //Windows
        List<Facture> commands = new ArrayList<>();
        Facture commande = factureRepository
                .findById(commandeId).
                        orElseThrow(() -> new ElementNotFoundException("commande"));
        commands.add(commande);
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:reports\\bill2.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(commands);
        JRBeanCollectionDataSource product = new JRBeanCollectionDataSource(commande.getProducts());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Parameter1", product);
        parameters.put("Commande", dataSource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        DataSource aAttachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
        effyis.partners.socle.entity.Account account = accountRepository.findByLogin(accountService.getConnectedUser()).get();
        sendBill(account.getCompanyName(), commande.getTotal(), commande.getCustomer().getFullName(), commande.getCustomer().getEmail(), aAttachment, commande.getId(), account.getCompanyName());
        JasperExportManager.exportReportToPdfFile(jasperPrint, "classpath:reports");
        File file1 = ResourceUtils.getFile("classpath:reports\\bill.pdf");
        FileInputStream inputStream = new FileInputStream(file1);
        MultipartFile multipartFile = new MockMultipartFile(file1.getName(), file1.getName(),ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
        String fileName=storageService.uploadFile(multipartFile);
        commande.setFileName(fileName);
        factureRepository.save(commande);
        
        //upload to s3
		/*
		 *  String path = "C:\\Users\\ASUS\\Desktop\\aaa";
		 * JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\bill.pdf");
		 * byte[] byte1 = JasperExportManager.exportReportToPdf(jasperPrint);
		 * System.err.println(byte1); Path path1 = Paths.get(path + "\\bill.pdf");
		 * Files.write(path1, byte1); uploadBillPdf(commande.getId(),
		 * ResourceUtils.getFile(path + "\\bill.pdf"));
		 */


    }
    //Linux------------------------------------------------------------------------------------------
    /*
    public void exportReport(Long commandeId) throws JRException, MessagingException, IOException {

        //Linux ec2
        String path = "/opt/effyis-pay/bill/aaa";

        List<Facture> factures = new ArrayList<>();
        Facture commande = factureRepository
                .findById(commandeId).
                        orElseThrow(() -> new ElementNotFoundException("commande"));
        factures.add(commande);
        //load file and compile it
        Resource resource = resourceLoader.getResource("classpath:reports/bill.jrxml");
        InputStream in = resource.getInputStream();
        File file = createTempFile(in);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(factures);
        JRBeanCollectionDataSource product = new JRBeanCollectionDataSource(commande.getProducts());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Parameter1", product);
        parameters.put("Commande", dataSource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        DataSource aAttachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/bill.pdf");
        byte[] byte1 = JasperExportManager.exportReportToPdf(jasperPrint);
        System.err.println(byte1);
        Path path1 = Paths.get(path + "/bill.pdf");
        Files.write(path1, byte1);
        uploadBillPdf(commande.getId(), ResourceUtils.getFile(path + "/bill.pdf"));
        effyis.partners.socle.entity.Account account = accountRepository.findByLogin(accountService.getConnectedUser()).get();
//        sendBill(account.getCompanyName(), commande.getTotal(), commande.getCustomer().getFullName(), commande.getCustomer().getEmail(), aAttachment, commande.getId(), account.getCompanyName());

    }
*/
    public void sendBill(String CompanyName, Long total, String fullName, String email, DataSource aAttachment, long numFacture, String companyName) throws MessagingException {
        MailDTO mailDto = new MailDTO();
        mailDto.setFrom(mailFrom);
        mailDto.setTo(email);
        mailDto.setSubject("Votre facture n°:" + numFacture
        //        + "de" + " " + companyName
        );
        mailDto.setTemplate("bill");
        mailDto.setDatasource(aAttachment);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("bill", aAttachment);
        props.put("total", total);
        props.put("fullName", fullName);
        //props.put("companyName", companyName);
        mailDto.setProps(props);
        mailService.sendMailPdf(mailDto);
    }

	@Override
	public List<FactureDTO> getListFacture() {
		List<FactureDTO> FactureDTOs =new ArrayList<>();
		List<Facture> Factures=factureRepository.findAll();

		for (Facture facture : Factures) {
			FactureDTO factureDTO = new FactureDTO();
			modelMaper.map(facture,factureDTO);
			factureDTO.setCustomerId(facture.getCustomer().getId());
			//factureDTO.setDeliveryId(facture.getDelivery().getId());
			factureDTO.setCouponId(facture.getCoupon().getId());
			FactureDTOs.add(factureDTO);
		}
		return FactureDTOs;
	}

	/*
	 * void uploadBillPdf(long articleId, File file) {
	 *
	 * //1. Grab some metadata from file if any Map<String, String> metadata = new
	 * HashMap<>(); metadata.put("Content-Type", "text/plain");
	 * metadata.put("Content-Length", String.valueOf(file.length())); //3. The user
	 * exists in our database Command command =
	 * commandeRepository.findById(articleId).orElse(null); //5. Store the image in
	 * s3 and update database (articleImageLink) with s3 image link String path =
	 * String.format("%s/%s", BucketName.FACTURE_PDF.getBucketName(),
	 * command.getId()); String filename = String.format("Facture %s", articleId);
	 * try { fileStoreService.save(path, filename, Optional.of(metadata), new
	 * FileInputStream(file)); } catch (IOException e) { throw new
	 * IllegalStateException(e); } }
	 *
	 * File createTempFile(InputStream in) throws IOException { File tempFile =
	 * File.createTempFile("bill", ".pdf"); tempFile.deleteOnExit();
	 * FileOutputStream out = new FileOutputStream(tempFile); IOUtils.copy(in, out);
	 * return tempFile; }
	 */

}

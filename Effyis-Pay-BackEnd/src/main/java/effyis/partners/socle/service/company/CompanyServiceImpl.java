package effyis.partners.socle.service.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import effyis.partners.socle.Util.CloudinaryUtil;
import effyis.partners.socle.Util.FileConverter;
import effyis.partners.socle.Util.FileSizeCalculator;
import effyis.partners.socle.dto.CustomCompanyDTO;
import effyis.partners.socle.dto.company.CompanyRequestDTO;
import effyis.partners.socle.dto.company.response.CompanyResponseDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.cloudinary.Attachement;
import effyis.partners.socle.entity.cloudinary.CloudinaryInformation;
import effyis.partners.socle.entity.company.Company;
import effyis.partners.socle.enums.CompanyType;
import effyis.partners.socle.enums.TypeAttachement;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.cloudinary.AttachementRepository;
import effyis.partners.socle.repository.cloudinary.CloudinaryInformationRepository;
import effyis.partners.socle.repository.company.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService, CloudinaryUtil {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CloudinaryInformationRepository cloudinaryInformationRepository;

	@Autowired
	AttachementRepository attachementRepository;

	@Autowired
	Cloudinary cloudinary;

	@Value("${attachementSize.logo}")
	private double logoSize;

	@Value("${attachementSize.product}")
	private double productSize;

	@Value("${attachementSize.category}")
	private double categorySize;

	@Override
	public CustomResponseDTO saveCompany(MultipartFile multipartfile, CompanyRequestDTO companyRequestDTO)
			throws IOException {
		CustomCompanyDTO customCompanyDTO = new CustomCompanyDTO();
		CustomResponseDTO customResponseDTO = new CustomResponseDTO();
		List<Company> companies = companyRepository.authenticatedUserCompanies(this.getConnectedUser().get().getId());
		Company company = null;
		if(companies.isEmpty()) {
			company = new Company();
			customCompanyDTO = this.uploadFile(multipartfile, "logo");
			this.buildCompanyObject(company, companyRequestDTO, customCompanyDTO);
		}
		else {
			for(Company companyTemp: companies) {
				company = companyTemp;
				customCompanyDTO = this.uploadFile(multipartfile, "logo");
				this.buildCompanyObject(company, companyRequestDTO, customCompanyDTO);
			}
		}
		companyRepository.save(company);
		customResponseDTO.setResponse(company.getId().toString());
		return customResponseDTO;
	}
	
	@Override
	public CustomResponseDTO updateCompany(MultipartFile multipartfile, CompanyResponseDTO companyResponseDTO)
			throws IOException {
		CustomCompanyDTO customCompanyDTO = new CustomCompanyDTO();
		CustomResponseDTO customResponseDTO = new CustomResponseDTO();
		Optional<Company> company = companyRepository.findById(companyResponseDTO.getId());
		customCompanyDTO = this.uploadFile(multipartfile, "logo");
		this.updateCompanyObject(company.get(), companyResponseDTO, customCompanyDTO);
		companyRepository.save(company.get());
		customResponseDTO.setResponse(company.get().getId().toString());
		return customResponseDTO;
	}

	@Override
	public List<CompanyResponseDTO> getAllCompanies() {
		List<Company> companies = companyRepository.authenticatedUserCompanies(this.getConnectedUser().get().getId());
		List<CompanyResponseDTO> companyResponseDTOS = new ArrayList<>();
		for (Company company : companies) {
			CompanyResponseDTO companyResponseDTO = new CompanyResponseDTO();
			modelMapper.map(company, companyResponseDTO);
			companyResponseDTOS.add(companyResponseDTO);
		}
		return companyResponseDTOS;
	}

	public Optional<Account> getConnectedUser() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return accountRepository.findByLogin(email);
	}

	public CompanyType setCompanyType(String input) {
		CompanyType companyType;
		if (input != null) {
			if (input.equals("Particulier / Micro-entrepreneur / Auto-entrepreneur"))
				companyType = CompanyType.PMO;
			else if (input.equals("Societe"))
				companyType = CompanyType.Societe;
			else if (input.equals("Association"))
				companyType = CompanyType.Association;
			else
				companyType = null;
		} else {
			companyType = null;
		}
		return companyType;
	}

	public CustomCompanyDTO uploadFile(MultipartFile multipartfile, String attachementType) throws IOException {
		List<String> imageTypes = new ArrayList<>();
		imageTypes.add("image/png");
		imageTypes.add("image/jpg");
		imageTypes.add("image/jpeg");
		CustomCompanyDTO urlResponseDTO = new CustomCompanyDTO();
		String message = null;
		File uploadedFile = FileConverter.convertMultiPartToFile(multipartfile); // convert MultipartFile to File
		if (TypeAttachement.LOGO.toString().equalsIgnoreCase(attachementType)) { // logo typeAttachement Case process
			if (FileSizeCalculator.filesizeInMegaBytes(uploadedFile) <= logoSize
					&& imageTypes.contains(multipartfile.getContentType())) {
				Map uploadResult = uploadGeneric(uploadedFile, cloudinary); // upload to Cloudinary
				FileConverter.cleanTemporary(uploadedFile); // Delete File from project
//				urlResponseDTO.setResponse("File uploaded successfully");
				CloudinaryInformation cloudinaryInformation = new CloudinaryInformation(); // Create a new
																							// CloudinaryInformation
																							// object set the data from
				setCloudinaryInfoGeneric(uploadResult, cloudinaryInformation); // set the data from Cloudinary into the
																				// object
				Attachement attachement = attachementRepository.findByTypeAttachement(TypeAttachement.LOGO);
				Long idAttachementTemp = attachementRepository.findByTypeAttachement(TypeAttachement.LOGO).getId();
				cloudinaryInformation.setAttachement(attachement); // Set Parent Child Foreign key relationship
				long result = cloudinaryInformationRepository.countInformationsByIdAttachement(idAttachementTemp);
				Attachement attachementTemp = attachementRepository.findByTypeAttachement(TypeAttachement.LOGO);
				attachementTemp.setActiveAttachement((int) result);
				cloudinaryInformationRepository.save(cloudinaryInformation);
				if (result >= 3) {
					List<CloudinaryInformation> cloudinaryInformations = cloudinaryInformationRepository
							.getListByAttachementId(idAttachementTemp);
					cloudinary.uploader().destroy(cloudinaryInformations.get(0).getPublic_id(), ObjectUtils.emptyMap());
					cloudinaryInformationRepository.delete(cloudinaryInformations.get(0));
					attachementTemp.setActiveAttachement((int) result - 1);
				}
				attachementRepository.save(attachementTemp);
				urlResponseDTO.setUrl(cloudinaryInformation.getSecure_url().toString());
				urlResponseDTO.setTest(true);
			} else {
				if (FileSizeCalculator.filesizeInMegaBytes(uploadedFile) > categorySize) { // File size not respected
																							// process
					urlResponseDTO.setUrl("Veillez selectionnner un fichier de taille maximal " + categorySize + " Mo");
					urlResponseDTO.setTest(false);
					FileConverter.cleanTemporary(uploadedFile);
				}
				if (!imageTypes.contains(multipartfile.getContentType())) { // File extension not respected process
					urlResponseDTO.setUrl("Veillez selectionnner un fichier de format png, jpg, ou jpeg");
					urlResponseDTO.setTest(false);
					FileConverter.cleanTemporary(uploadedFile);
				}
			}
		}
		return urlResponseDTO;

	}

	public String checkStringValue(String input, boolean test) {
		if (test == true) {
			return input;
		} else
			return "";
	}

	public void buildCompanyObject(Company company, CompanyRequestDTO companyDTO, CustomCompanyDTO customCompanyDTO) {
		company.setAdress(companyDTO.getAdress());
		company.setCity(companyDTO.getCity());
		company.setCurrency(companyDTO.getCurrency());
		company.setCountry(companyDTO.getCountry());
		company.setWebSite(companyDTO.getWebSite());
		company.setCompanyType(this.setCompanyType(companyDTO.getCompanyType()));
		company.setCompanyName(companyDTO.getCompanyName());
		company.setPostalCode(companyDTO.getPostalCode());
		company.setRegion(companyDTO.getRegion());
		company.setTvaNumber(companyDTO.getTvaNumber());
		company.setCompanyEmail(companyDTO.getCompanyEmail());
		company.setImageUrl(this.checkStringValue(customCompanyDTO.getUrl(), customCompanyDTO.isTest()));
		company.setAccount(this.getConnectedUser().get());
	}
	
	public void updateCompanyObject(Company company, CompanyResponseDTO companyDTO, CustomCompanyDTO customCompanyDTO) {
		company.setAdress(companyDTO.getAdress());
		company.setCity(companyDTO.getCity());
		company.setCurrency(companyDTO.getCurrency());
		company.setCountry(companyDTO.getCountry());
		company.setWebSite(companyDTO.getWebSite());
		company.setCompanyType(this.setCompanyType(companyDTO.getCompanyType()));
		company.setCompanyName(companyDTO.getCompanyName());
		company.setPostalCode(companyDTO.getPostalCode());
		company.setRegion(companyDTO.getRegion());
		company.setTvaNumber(companyDTO.getTvaNumber());
		company.setCompanyEmail(companyDTO.getCompanyEmail());
		company.setImageUrl(this.checkStringValue(customCompanyDTO.getUrl(), customCompanyDTO.isTest()));
		company.setAccount(this.getConnectedUser().get());
	}
}

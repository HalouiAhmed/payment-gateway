package effyis.partners.socle.service.product;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import effyis.partners.socle.Util.CSVHelper.CSVHelper;
import effyis.partners.socle.Util.response.MessageCSVResponse;
import effyis.partners.socle.dto.StockDTO;
import effyis.partners.socle.dto.product.CategoryDTO;
import effyis.partners.socle.dto.product.ImageProductDTO;
import effyis.partners.socle.dto.product.ProductDTO;
import effyis.partners.socle.dto.product.request.ProductModifyInfoRequest;
import effyis.partners.socle.dto.product.request.ProductSaveRequest;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.cloudinary.CloudinaryInformation;
import effyis.partners.socle.entity.product.Category;
import effyis.partners.socle.entity.product.Product;
import effyis.partners.socle.enums.TypeAttachement;
import effyis.partners.socle.exception.ElementNotFoundException;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.cloudinary.CloudinaryInformationRepository;
import effyis.partners.socle.repository.product.CategoryReposiroty;
import effyis.partners.socle.repository.product.ProductRepository;
import effyis.partners.socle.service.AccountService;

/**
 * @author CHICHI Hamza
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryReposiroty categoryReposiroty;

    @Autowired
    CloudinaryInformationRepository cloudinaryInformationRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    Cloudinary cloudinary;

    @Override
    public List<ProductDTO> getProducts(String categoryParam) {
        Account account = this.getConnectedAccount();

        List<Product> products ;
        if (categoryParam == null){
            products = productRepository.findByAccount_Id(account.getId());
        }
        else {
            Category categoryOptional = categoryReposiroty.findByCategoryNameAndAccount_Id(categoryParam,account.getId())
                    .orElseThrow(() -> new ElementNotFoundException("category " + categoryParam));
            products = productRepository.findByCategoryAndAccount_Id(categoryOptional,account.getId());
        }

        List<ProductDTO> productResponse = new ArrayList<>();

        products.forEach(product -> {

            ProductDTO productDTO = mapProductToProductDTO(product);

            this.modelMapper.map(product,productDTO);
            productResponse.add(productDTO);
        });
        
        return productResponse;
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Account account = this.getConnectedAccount();

        Product product = productRepository.findByIdAndAccount_Id(id,account.getId())
                .orElseThrow(() -> new ElementNotFoundException("product with id " + id));

        ProductDTO productDTO = mapProductToProductDTO(product);

        this.modelMapper.map(product,productDTO);
        return productDTO;
    }

    @Override
    public List<StockDTO> getStock() {
        Account account = this.getConnectedAccount();
        List<Product> products = new ArrayList<>();
        products = productRepository.findByAccount_Id(account.getId());
        List<StockDTO> stocks = new ArrayList<>();
        products.forEach(product -> {

            StockDTO stockDTO = mapProductToStock(product);
            this.modelMapper.map(product,stockDTO);
            stocks.add(stockDTO);
        });
        return stocks;
    }

    @Override
    public ResponseEntity<MessageCSVResponse> uploadProductCSV(MultipartFile file) {
        String email = accountService.getConnectedUser();
        Account account = accountRepository.findByLogin(email).orElse(null);
        String message = "";
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                List<Product> products = CSVHelper.csvToProduct(file.getInputStream());
                products.forEach(product -> {product.setAccount(account);});
                productRepository.saveAll(products);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new MessageCSVResponse(message));
            } catch (IOException e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
        }
        else {
            message = "Please upload a csv file!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageCSVResponse(message));
        }    }

    @Override
    public ByteArrayInputStream downloadProductCSV() {
        String email = accountService.getConnectedUser();
        Account account = accountRepository.findByLogin(email).orElse(null);
        List<Product> products = productRepository.findByAccount_Id(account.getId());

        ByteArrayInputStream inputStream = CSVHelper.productsToCsv(products);
        return inputStream;    }

    @Override
    public void saveProduct(ProductSaveRequest productSaveRequest) {
        Account account = this.getConnectedAccount();

        Product product = new Product();
        List<CloudinaryInformation> imageProduct = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        CloudinaryInformation cloudinaryInformation ;
        Category categoryOptional ;

        for (Long id :  productSaveRequest.getIdsImage() ) {
            cloudinaryInformation = cloudinaryInformationRepository.findById(id)
                    .orElseThrow(() -> new ElementNotFoundException("image with id " + id));

            if(!cloudinaryInformation.getAttachement().getTypeAttachement().name().equals(TypeAttachement.PRODUCT.name())){
                throw  new ElementNotFoundException("image with id " + id + " of type product");
            }

            imageProduct.add(cloudinaryInformation);
        }

        for (CategoryDTO category : productSaveRequest.getCategories()){
            categoryOptional = categoryReposiroty.findByCategoryNameAndAccount_Id(category.getCategoryName(),account.getId())
                    .orElseThrow(() -> new ElementNotFoundException("category " + category.getCategoryName()));
            categories.add(categoryOptional);
        }

        product.setImagesProduct(imageProduct);
        product.setCategory(categories);
        product.setAccount(account);
        this.modelMapper.map(productSaveRequest,product);
        productRepository.save(product);

    }

    @Override
    public void deleteProduct(Long id) throws IOException {
        Account account = this.getConnectedAccount();

        Product product = productRepository.findByIdAndAccount_Id(id,account.getId())
                .orElseThrow(() -> new ElementNotFoundException("product"));

        for (CloudinaryInformation cloudinaryInformation :  product.getImagesProduct() ) {
            cloudinary.uploader().destroy(cloudinaryInformation.getPublic_id(), new HashMap());
        }
        product.getImagesProduct().removeIf(item -> item.getId().equals(id));
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void modifyProductInfo(Long id, ProductModifyInfoRequest productModifyInfoRequest) {
        Account account = this.getConnectedAccount();

        Product product = productRepository.findByIdAndAccount_Id(id,account.getId())
                .orElseThrow(() -> new ElementNotFoundException("product with id " + id));

        String name = productModifyInfoRequest.getName();
        Double price = productModifyInfoRequest.getPrice();
        List<CategoryDTO> categories = productModifyInfoRequest.getCategories();
        String description = productModifyInfoRequest.getDescription();
        String devise = productModifyInfoRequest.getDevise();
        int stock = productModifyInfoRequest.getStock();
        int sold = productModifyInfoRequest.getSold();

        if (name != null &&
                !Objects.equals(product.getName(), name)){
            product.setName(name);
        }
        if (price != null &&
                !Objects.equals(product.getPrice(), price)){
            product.setPrice(price);
        }
        if (devise != null &&
                !Objects.equals(product.getDevise(), devise)){
            product.setDevise(devise);
        }
        if (categories != null){
            //product.setCategory(null);
            List<Category> categoryList = new ArrayList<>();
            for (CategoryDTO categoryDTO : categories){
                Category category = categoryReposiroty.findByCategoryNameAndAccount_Id(categoryDTO.getCategoryName(),account.getId())
                        .orElseThrow(() -> new ElementNotFoundException("category  " + categoryDTO.getCategoryName()));
                categoryList.add(category);
            }
            product.setCategory(categoryList);
        }
        if (description != null &&
                !Objects.equals(product.getDescription(), description)){
            product.setDescription(description);
        }
        if(!Objects.equals(product.getStock(), stock)){
            product.setStock(stock);
        }
        if(!Objects.equals(product.getSold(), sold)){
            product.setSold(sold);
        }
    }

    @Override
    @Transactional
    public void modifyProductImages(Long id, List<Long> idsImage) throws IOException {
        Account account = this.getConnectedAccount();

        Product product = productRepository.findByIdAndAccount_Id(id,account.getId())
                .orElseThrow(() -> new ElementNotFoundException("product with id " + id));

        List<CloudinaryInformation> currentImages = product.getImagesProduct();
        List<CloudinaryInformation> newImageList = new ArrayList<>();

        idsImage.forEach(idImage ->{
            CloudinaryInformation cloudinaryInformationTemp =  cloudinaryInformationRepository.findById(idImage)
                    .orElseThrow(() -> new ElementNotFoundException("image with id " + idImage));

            //Add new image to the product
            if (!containsId(currentImages,cloudinaryInformationTemp.getId())){
                newImageList.add(cloudinaryInformationTemp);
            }
        });

        product.getImagesProduct().addAll(newImageList);
        productRepository.save(product); //Save product with the new images

        List<Long> idsToRemove = this.getImageIdsToRemove(currentImages,idsImage);

        for (Long idToRemove: idsToRemove) {
            CloudinaryInformation cloudinaryInformationsToRemove = cloudinaryInformationRepository.findById(idToRemove)
                    .orElseThrow(() -> new ElementNotFoundException("image with id " + idToRemove));
            cloudinary.uploader().destroy(cloudinaryInformationsToRemove.getPublic_id(), new HashMap()); //Remove images from cloudinary

            product.getImagesProduct().removeIf(item -> item.getId().equals(idToRemove));

            cloudinaryInformationRepository.deleteById(idToRemove);
        }
    }


    //Logic Methods_____________________________________________________________________________

    List<Long> getImageIdsToRemove(List<CloudinaryInformation> currentImages, List<Long> idsImage) {
        List<Long> idsPresent  = new ArrayList<>();
        for (CloudinaryInformation currentImage : currentImages) {
            idsPresent.add(currentImage.getId());
        }
        List<Long> idsToRemove = new ArrayList<>(idsPresent);
        idsToRemove.removeAll(idsImage);
        return idsToRemove;
    }

    public boolean containsId(final List<CloudinaryInformation> list, final Long id){
        return list.stream().map(CloudinaryInformation::getId).anyMatch(id::equals);
    }
    private StockDTO mapProductToStock(Product product){
        StockDTO stockDTO = new StockDTO();
        stockDTO.setStock(product.getStock());
        stockDTO.setSold(product.getSold());
        return stockDTO;
    }

    private ProductDTO mapProductToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setImages(new ArrayList<>());
        productDTO.setCategories(new ArrayList<>());
        product.getImagesProduct().forEach(image -> {

            Long idImage = image.getId();
            String url = image.getSecure_url();

            ImageProductDTO imageProductDTO = new ImageProductDTO(idImage, url);
            productDTO.getImages().add(imageProductDTO);
        });
        product.getCategory().forEach(category -> {
            CategoryDTO categoryDTO = new CategoryDTO(category.getCategoryName());
            productDTO.getCategories().add(categoryDTO);
        });
        return productDTO;
    }

    private Account getConnectedAccount(){
        String email = accountService.getConnectedUser();
        return accountRepository.findByLogin(email).orElseThrow(
                () -> new ElementNotFoundException("account"));
    }
}

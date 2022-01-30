package effyis.partners.socle.service.product;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import effyis.partners.socle.Util.response.MessageCSVResponse;
import effyis.partners.socle.dto.StockDTO;
import effyis.partners.socle.dto.product.ProductDTO;
import effyis.partners.socle.dto.product.request.ProductModifyInfoRequest;
import effyis.partners.socle.dto.product.request.ProductSaveRequest;

/**
 * @author CHICHI Hamza
 */

public interface ProductService {
    List<ProductDTO> getProducts(String category);

    void saveProduct(ProductSaveRequest productSaveRequest);

    void deleteProduct(Long id) throws IOException;

    void modifyProductInfo(Long id, ProductModifyInfoRequest productModifyInfoRequest);

    void modifyProductImages(Long id, List<Long> idsImage) throws IOException;

    ProductDTO getProductById(Long id);
    List<StockDTO> getStock();
    ResponseEntity<MessageCSVResponse> uploadProductCSV(MultipartFile file);
    ByteArrayInputStream downloadProductCSV();
}

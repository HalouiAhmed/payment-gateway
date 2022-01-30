package effyis.partners.socle.controller.product;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import effyis.partners.socle.Util.response.MessageCSVResponse;
import effyis.partners.socle.dto.product.ProductDTO;
import effyis.partners.socle.dto.product.request.ProductModifyInfoRequest;
import effyis.partners.socle.dto.product.request.ProductSaveRequest;
import effyis.partners.socle.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


/**
 * @author CHICHI Hamza
 */

@RestController
@RequestMapping("/effyis/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public List<ProductDTO> getProducts(@RequestParam(required = false) String category){
        return productService.getProducts(category);
    }

    @GetMapping("/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public ProductDTO getProductById(@PathVariable ("id") Long id){
        return productService.getProductById(id);
    }

    @PostMapping()
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public void saveProduct(@RequestBody ProductSaveRequest productSaveRequest){
        productService.saveProduct(productSaveRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public void deleteProduct(@PathVariable ("id") Long id) throws IOException {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public void modifyProductInfo(@PathVariable ("id") Long id,
                                  @RequestBody ProductModifyInfoRequest productModifyInfoRequest){
        productService.modifyProductInfo(id, productModifyInfoRequest);
    }

    @PutMapping("product/{id}/images")
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public void modifyProductImages(@PathVariable ("id") Long id,
                                  @RequestBody List<Long> IdsImage) throws IOException {
        productService.modifyProductImages(id, IdsImage);
    }
    @PostMapping("/upload")
    public ResponseEntity<MessageCSVResponse> uploadCustomerCSV(@RequestParam("file") MultipartFile file){
        return productService.uploadProductCSV(file);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "products.csv";
        InputStreamResource file = new InputStreamResource(productService.downloadProductCSV());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
    
}

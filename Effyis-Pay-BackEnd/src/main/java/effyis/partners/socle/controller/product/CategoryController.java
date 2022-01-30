package effyis.partners.socle.controller.product;


import effyis.partners.socle.dto.product.CategoryDTO;
import effyis.partners.socle.dto.product.request.CategorySaveRequest;
import effyis.partners.socle.service.product.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author CHICHI Hamza
 */

@RestController
@RequestMapping("/effyis/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable ("id") Long id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping()
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public void saveCategory(@RequestBody CategorySaveRequest categorySaveRequest){
        categoryService.saveCategory(categorySaveRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public void deleteCategory(@PathVariable ("id") Long id) throws IOException {
        categoryService.deleteCategory(id);
    }

    @PutMapping("/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public void editCategoryName(@PathVariable ("id") Long id,@RequestBody String categoryName ){
        categoryService.editCategoryName(id,categoryName);
    }

    @PutMapping("/{id}/image")
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public void editCategoryImage(@PathVariable ("id") Long id, @RequestBody Long idImage) throws IOException {
        categoryService.editCategoryImage(id, idImage);
    }
}

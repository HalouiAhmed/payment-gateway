package effyis.partners.socle.service.product;


import effyis.partners.socle.dto.product.CategoryDTO;
import effyis.partners.socle.dto.product.request.CategorySaveRequest;

import java.io.IOException;
import java.util.List;

/**
 * @author CHICHI Hamza
 */

public interface CategoryService {
    List<CategoryDTO> getCategories();

    void saveCategory(CategorySaveRequest categorySaveRequest);

    void deleteCategory(Long id) throws IOException;

    void editCategoryName(Long id,String categoryName);

    void editCategoryImage(Long id, Long idImage) throws IOException;

    CategoryDTO getCategoryById(Long id);
}

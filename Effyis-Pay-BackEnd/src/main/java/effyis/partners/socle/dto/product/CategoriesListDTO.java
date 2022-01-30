package effyis.partners.socle.dto.product;

import java.util.List;

/**
 * @author CHICHI Hamza
 */

public class CategoriesListDTO {

    List<CategoryDTO> categories;

    public CategoriesListDTO(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}

package effyis.partners.socle.dto.product.request;

/**
 * @author CHICHI Hamza
 */

public class CategorySaveRequest {

    private String categoryName;
    private Long categoryImage;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(Long categoryImage) {
        this.categoryImage = categoryImage;
    }
}

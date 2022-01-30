package effyis.partners.socle.dto.product.request;



import effyis.partners.socle.dto.product.CategoryDTO;

import java.util.List;

/**
 * @author CHICHI Hamza
 */

public class ProductSaveRequest {

    private String name;
    private Double price;
    private String devise;
    private List<CategoryDTO> categories;
    private String description;
    private List<Long> idsImage;
    private int stock;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> category) {
        this.categories = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<Long> getIdsImage() {
        return idsImage;
    }

    public void setIdsImage(List<Long> idsImage) {
        this.idsImage = idsImage;
    }

    public ProductSaveRequest() {
    }

    @Override
    public String toString() {
        return "ProductSaveRequest{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", category='" + categories + '\'' +
                ", description='" + description + '\'' +
                ", idsImage=" + idsImage +
                '}';
    }
}

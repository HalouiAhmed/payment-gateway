package effyis.partners.socle.dto.product.request;



import effyis.partners.socle.dto.product.CategoryDTO;

import java.util.List;

/**
 * @author CHICHI Hamza
 */

public class ProductModifyInfoRequest {

    private String name;
    private Double price;
    private String devise;
    private List<CategoryDTO> categories;
    private String description;
    private String tags;
    private int stock;
    private int sold;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public ProductModifyInfoRequest() {
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

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ProductModifyInfoRequest{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", category='" + categories + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}

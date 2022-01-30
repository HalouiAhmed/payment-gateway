package effyis.partners.socle.dto.product;

import java.util.List;


/**
 * @author CHICHI Hamza
 */

public class ProductDTO {

	private Long id;
	private String name;
	private Double price;
	private List<CategoryDTO> categories;
	private String description;
	private List<ImageProductDTO> images;
     private Double quantity;

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public ProductDTO() {
    }

    public ProductDTO(Long id, String name, Double price, List<CategoryDTO> categories, String description, String tags, List<ImageProductDTO> images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categories = categories;
        this.description = description;
        this.images = images;
    }

    public ProductDTO(String name, Double price, List<CategoryDTO> categories, String description, String tags, List<ImageProductDTO> images) {
        this.name = name;
        this.price = price;
        this.categories = categories;
        this.description = description;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    

    public List<ImageProductDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageProductDTO> images) {
        this.images = images;
    }


    @Override
    public String toString() {
        return "ProductDTO [id=" + id + ", name=" + name + ", price=" + price + ", categories=" + categories
                + ", description=" + description + ", tags=" +  ", images=" + images + "]";
    }


}

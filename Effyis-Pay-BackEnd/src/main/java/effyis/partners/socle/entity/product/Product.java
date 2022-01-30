package effyis.partners.socle.entity.product;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.cloudinary.CloudinaryInformation;
import effyis.partners.socle.entity.command.Facture;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * @author CHICHI Hamza
 */

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantity;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double price;
    private String devise = "MAD";
    private String description;
    private int stock;
    private int sold; 

    public Product(String name, Double price, String devise, String description, int stock) {
        this.name = name;
        this.price = price;
        this.devise = devise;
        this.description = description;
        this.stock = stock;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "product_by_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> category;

    @ManyToMany(mappedBy = "products")
    private List<Facture> commandes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
    
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(name = "product_by_cloudinaryinformation",
            joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "cloudinary_information_id")
    )
    private List<CloudinaryInformation> imagesProduct;

    public Product() {
    }

    public Product(String name, Double price, String devise, String description, List<Category> category, Account account, List<CloudinaryInformation> imagesProduct) {
        this.name = name;
        this.price = price;
        this.devise = devise;
        this.description = description;
        this.category = category;
        this.account = account;
        this.imagesProduct = imagesProduct;
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

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<CloudinaryInformation> getImagesProduct() {
        return imagesProduct;
    }

    public void setImagesProduct(List<CloudinaryInformation> imagesProduct) {
        this.imagesProduct = imagesProduct;
    }

    public Account getAccount() {
        return account;
    }

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

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Facture> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Facture> commandes) {
        this.commandes = commandes;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product [id=" + this.id + ", name=" + this.name + ", price=" + this.price + ", category=" + this.category + ", description="
                + this.description + ", imagesProduct=" + this.imagesProduct + ", account="+ this.account+"]";
    }

}

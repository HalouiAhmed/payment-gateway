package effyis.partners.socle.entity.product;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.cloudinary.CloudinaryInformation;

import javax.persistence.*;
import java.util.List;

/**
 * @author CHICHI Hamza
 */

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @OneToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true, optional = false)
    private CloudinaryInformation categoryImage;

    @ManyToMany(mappedBy = "category",cascade = {CascadeType.REMOVE})
    private List<Product> products;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    public Category() {
    }

    public Category(String categoryName, CloudinaryInformation categoryImage, Account account) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.account = account;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public CloudinaryInformation getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(CloudinaryInformation categoryImage) {
        this.categoryImage = categoryImage;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

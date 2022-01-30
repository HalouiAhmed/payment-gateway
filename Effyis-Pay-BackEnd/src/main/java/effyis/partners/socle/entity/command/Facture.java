package effyis.partners.socle.entity.command;

import effyis.partners.socle.coupon.entity.Coupon;
import effyis.partners.socle.entity.Customer;
import effyis.partners.socle.entity.product.Product;
import effyis.partners.socle.enums.Etat;
import effyis.partners.socle.shippingCost.entity.ShippingCost;
import effyis.partners.socle.taxeRate.entity.TaxeRate;

import javax.persistence.*;


import java.util.Date;
import java.util.List;
/**
 * @author IMerghichi
 */
@Entity
@Table(name = "facture")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private ShippingCost delivery;
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    		name ="facture_by_product",
       		joinColumns =@JoinColumn(name = "facture_id"),
       		inverseJoinColumns = @JoinColumn(name="product_id")
    		)
    private List<Product> products;
    @ManyToOne
    @JoinColumn(name ="customer_id")
    private Customer customer;
    @ManyToOne
	@JoinColumn(name = "tax_id")
	private TaxeRate taxeRate;
    private String description;
    private Date invoiceDate;
    private Date dueDate;
    private double sousTotale;
    private String logoUrl;
    private Long total;
    private String footer;
    private String fileName;
private Etat etatCommande = Etat.EnCours;

	public Etat getEtatCommande() {
		return etatCommande;
	}
	public void setEtatCommande(Etat etatCommande) {
		this.etatCommande = etatCommande;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public TaxeRate getTaxeRate() {
		return taxeRate;
	}

	public void setTaxeRate(TaxeRate taxeRate) {
		this.taxeRate = taxeRate;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public double getSousTotale() {
		return sousTotale;
	}
	public void setSousTotale(double sousTotale) {
		this.sousTotale = sousTotale;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ShippingCost getDelivery() {
		return delivery;
	}
	public void setDelivery(ShippingCost delivery) {
		this.delivery = delivery;
	}
	public Coupon getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Facture(ShippingCost delivery, Coupon coupon, List<Product> products, Customer customer, String description,
				   Date invoiceDate, Date dueDate) {
		super();
		this.delivery = delivery;
		this.coupon = coupon;
		this.products = products;
		this.customer = customer;
		this.description = description;
		this.invoiceDate = invoiceDate;
		this.dueDate = dueDate;
	}
	public Facture() {
	}


}

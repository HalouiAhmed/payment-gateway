package effyis.partners.socle.dto.commande;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;

import effyis.partners.socle.dto.product.ProductDTO;
import effyis.partners.socle.enums.Etat;


public class FactureDTO {
	private long id;
    private Long deliveryId;
    private Long couponId;
    private List<ProductDTO> products;
    private Long customerId;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date invoiceDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;
    private double sousTotale;
    private String logoUrl;
    private Long total;
    private String footer;
    @Enumerated(EnumType.STRING)
    private Etat etatCommande = Etat.EnCours;
    private Long taxRate;
    private String fileName;
     
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getId() {
		return id;
	}

	public Long getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Long taxRate) {
		this.taxRate = taxRate;
	}

	public String getFooter() {
		return footer;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public List<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	public double getSousTotale() {
		return sousTotale;
	}
	public void setSousTotale(double sousTotale) {
		this.sousTotale = sousTotale;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public Etat getEtatCommande() {
		return etatCommande;
	}
	public void setEtatCommande(Etat etatCommande) {
		this.etatCommande = etatCommande;
	}
	public FactureDTO(Long deliveryId, Long couponId, List<ProductDTO> products, Long customerId, String description,
			Date invoiceDate, Date dueDate, double sousTotale, String logoUrl, Long total, String footer,
			Etat etatCommande) {
		super();
		this.deliveryId = deliveryId;
		this.couponId = couponId;
		this.products = products;
		this.customerId = customerId;
		this.description = description;
		this.invoiceDate = invoiceDate;
		this.dueDate = dueDate;
		this.sousTotale = sousTotale;
		this.logoUrl = logoUrl;
		this.total = total;
		this.footer = footer;
		this.etatCommande = etatCommande;
	}
	public FactureDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CommandeDTO [deliveryId=" + deliveryId + ", couponId=" + couponId + ", products=" + products
				+ ", customerId=" + customerId + ", description=" + description + ", invoiceDate=" + invoiceDate
				+ ", dueDate=" + dueDate + ", sousTotale=" + sousTotale + ", logoUrl=" + logoUrl + ", total=" + total
				+ ", footer=" + footer + ", etatCommande=" + etatCommande + "]";
	}


}

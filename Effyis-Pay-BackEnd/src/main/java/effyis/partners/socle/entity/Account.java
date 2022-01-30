package effyis.partners.socle.entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import effyis.partners.socle.coupon.entity.Coupon;
import effyis.partners.socle.entity.company.Company;
import effyis.partners.socle.shippingCost.entity.ShippingCost;
import effyis.partners.socle.taxeRate.entity.TaxeRate;

/**
 *
 * @author Elkotb Zakaria
 *
 */

@Entity
@Table(name = "account"		)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "login",nullable = false, unique = true)
	@NotNull
	private String login;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(nullable = false)
	@NotNull
	private String password;
	private Boolean verifiedEmail = false;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Role role;
	private String companyName;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "account", orphanRemoval = true)
	private List<Coupon> coupons;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "account", orphanRemoval = true)
	private List<ShippingCost> shippingCost;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "account", orphanRemoval = true)
	private List<TaxeRate> taxeRate;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "account", orphanRemoval = true)
	private List<Company> companies;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "account", orphanRemoval = true)
	private List<Coupon> bankAccounts;

	@Override
	public String toString() {
		StringBuilder account = new StringBuilder("{ login : ");
		return account.append(this.login).append(", password : ").append(this.password).append(" }")
				.append(",verifiedMail").append(this.verifiedEmail).toString();
	}

public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean isVerifiedEmail() {
		return verifiedEmail;
	}

	public void setVerifiedEmail(Boolean verifiedEmail) {
		this.verifiedEmail = verifiedEmail;
	}
	
	public Boolean getVerifiedEmail() {
		return verifiedEmail;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public List<ShippingCost> getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(List<ShippingCost> shippingCost) {
		this.shippingCost = shippingCost;
	}

	public List<TaxeRate> getTaxeRate() {
		return taxeRate;
	}

	public void setTaxeRate(List<TaxeRate> taxeRate) {
		this.taxeRate = taxeRate;
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(@NotNull String login, @NotNull String password, String companyName) {
		this.login = login;
		this.password = password;
//		this.companyName=companyName;
	}

	public Account(@NotNull String login, @NotNull String password, Boolean verifiedEmail, Role role,
			String companyName) {
		this.login = login;
		this.password = password;
		this.verifiedEmail = verifiedEmail;
		this.role = role;
//		this.companyName=companyName;
	}
}

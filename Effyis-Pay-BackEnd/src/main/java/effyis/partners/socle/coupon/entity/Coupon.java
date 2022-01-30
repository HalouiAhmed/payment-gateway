package effyis.partners.socle.coupon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.enums.CouponType;

@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private double percentage;

	@Column(nullable = false)
	private String code;
	@Column(name = "amount")
	private double amount;
	private CouponType couponType;
	@Column(name = "sous_total")
	private double sousTotal = 0;
	private boolean activated;
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getSousTotal() {
		return sousTotal;
	}

	public void setSousTotal(double sousTotal) {
		this.sousTotal = sousTotal;
	}

	public Coupon(String name, double percentage, String code) {
		this.name = name;
		this.percentage = percentage;
		this.code = code;
	}

	public Coupon() {

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

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}


	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CouponType getCouponType() {
		return couponType;
	}

	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}


	@Override
	public String toString() {
		return "Coupon [id=" + id + ", name=" + name + ", percentage=" + percentage + ", code=" + code + ", activated="
				+ activated + "]";
	}
}
package effyis.partners.socle.coupon.dto.response;


import effyis.partners.socle.enums.CouponType;

public class CouponResponseDTO {

	private Long id;
	private String name;
	private double percentage;
	private String code;
	private boolean activated;
	private CouponType couponType;
	private double amount;

	public CouponType getCouponType() {
		return couponType;
	}

	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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
	@Override
	public String toString() {
		return "CouponResponseDTO [id=" + id + ", name=" + name + ", percentage=" + percentage + ", code=" + code
				+ ", activated=" + activated + "]";
	}
}
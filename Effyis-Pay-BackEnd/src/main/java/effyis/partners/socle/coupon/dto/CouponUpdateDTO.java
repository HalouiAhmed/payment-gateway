package effyis.partners.socle.coupon.dto;

import effyis.partners.socle.enums.CouponType;

public class CouponUpdateDTO {

	private String code;
	private boolean activated;
	private String name;
	private CouponType couponType;
	private double percentage;

	public CouponType getCouponType() {
		return couponType;
	}

	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
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

	@Override
	public String toString() {
		return "CouponUpdateDTO [code=" + code + ", activated=" + activated + "]";
	}

}

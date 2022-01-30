package effyis.partners.socle.coupon.service;

import java.util.List;

import effyis.partners.socle.coupon.dto.CouponDTO;
import effyis.partners.socle.coupon.dto.CouponUpdateDTO;
import effyis.partners.socle.coupon.dto.response.CouponResponseDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;

public interface CouponService {
	CustomResponseDTO saveCoupon(CouponDTO couponDTO);
	List<CouponResponseDTO> getAllCoupons();
	CustomResponseDTO updateCoupon(CouponUpdateDTO updateDTO);
	void deleteCoupon(Long id);
	CouponResponseDTO getCoupon(String code);

}
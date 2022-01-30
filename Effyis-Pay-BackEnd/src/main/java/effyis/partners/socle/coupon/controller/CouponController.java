package effyis.partners.socle.coupon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import effyis.partners.socle.coupon.dto.CouponDTO;
import effyis.partners.socle.coupon.dto.CouponUpdateDTO;
import effyis.partners.socle.coupon.dto.response.CouponResponseDTO;
import effyis.partners.socle.coupon.service.CouponService;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/effyis/api/coupons")
public class CouponController {

	@Autowired
	CouponService couponService;
	
	@PostMapping
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	CustomResponseDTO saveCoupon(@RequestBody CouponDTO couponDTO) {
		return couponService.saveCoupon(couponDTO);
	}
	
	@GetMapping(path = "/{code}")
	CouponResponseDTO getCoupon(@PathVariable("code") String code) {
		return couponService.getCoupon(code);
	}
	
	@GetMapping
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	List<CouponResponseDTO> getCoupons() {
		return couponService.getAllCoupons();
	}
	
/*	@PutMapping
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	CustomResponseDTO updateCoupon(@RequestBody CouponUpdateDTO updateDTO) {
		return couponService.updateCoupon(updateDTO);
	}
*/
	@PutMapping("/delete")
	@Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	void deleteCoupon(@RequestParam Long id){
		couponService.deleteCoupon(id);
	}

}
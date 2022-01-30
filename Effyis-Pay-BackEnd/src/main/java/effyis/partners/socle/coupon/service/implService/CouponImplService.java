package effyis.partners.socle.coupon.service.implService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import effyis.partners.socle.coupon.dto.CouponDTO;
import effyis.partners.socle.coupon.dto.CouponUpdateDTO;
import effyis.partners.socle.coupon.dto.response.CouponResponseDTO;
import effyis.partners.socle.coupon.entity.Coupon;
import effyis.partners.socle.coupon.repository.CouponRepository;
import effyis.partners.socle.coupon.service.CouponService;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.repository.AccountRepository;

@Service
public class CouponImplService implements CouponService {

	@Autowired
	ModelMapper mapper;

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	AccountRepository accountRepository;

	@Override
	public CustomResponseDTO saveCoupon(CouponDTO couponDTO) {
		Coupon coupon = new Coupon();
		CustomResponseDTO customResponseDTO = new CustomResponseDTO();
		mapper.map(couponDTO, coupon);
		Optional<Account> authenticatedUser = this.getConnectedUser();
		coupon.setAccount(authenticatedUser.get());
		couponRepository.save(coupon);
		customResponseDTO.setResponse(coupon.getId().toString());
		return customResponseDTO;
	}

	@Override
	public CouponResponseDTO getCoupon(String code) {
		Optional<Coupon> coupon = couponRepository.findByCodeAndAccount(code, this.getConnectedUser().get().getId());
		CouponResponseDTO couponResponseDTO = new CouponResponseDTO();
		if (coupon.isPresent()) {
			mapper.map(coupon.get(), couponResponseDTO);
		} else {
			couponResponseDTO = null;
		}
		return couponResponseDTO;
	}

	@Override
	public List<CouponResponseDTO> getAllCoupons() {
		List<Coupon> coupons = couponRepository.authenticatedUserCoupons(this.getConnectedUser().get().getId());
		List<CouponResponseDTO> couponResponseDTOS = new ArrayList<>();
		for (Coupon coupon : coupons) {
			if(coupon.isActivated()) {
				CouponResponseDTO couponResponseDTO = new CouponResponseDTO();
				mapper.map(coupon, couponResponseDTO);
				couponResponseDTOS.add(couponResponseDTO);
			}
		}
		return couponResponseDTOS;
	}

	@Override
	public CustomResponseDTO updateCoupon(CouponUpdateDTO updateDTO) {
		CustomResponseDTO customResponseDTO = new CustomResponseDTO();
		Optional<Coupon> coupon = couponRepository.findByCodeAndAccount(updateDTO.getCode(),
				this.getConnectedUser().get().getId());
		if (coupon.isPresent()) {
			coupon.get().setActivated(updateDTO.isActivated());
			couponRepository.save(coupon.get());
			customResponseDTO
					.setResponse("code: " + coupon.get().getCode() + " - status: " + coupon.get().isActivated());
		} else {
			customResponseDTO.setResponse("no coupon with code " + updateDTO.getCode() + " existing");
		}
		return customResponseDTO;
	}

	@Override
	public void deleteCoupon(Long id) {
		Optional<Coupon> coupon = couponRepository.findByidAndAccount(id,
				this.getConnectedUser().get().getId());
		if (coupon.isPresent()) {
			coupon.get().setActivated(false);
			couponRepository.save(coupon.get());
		}
	}

	public Optional<Account> getConnectedUser() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return accountRepository.findByLogin(email);
	}

	public boolean checkIfExisting(String code) {
		List<Coupon> coupons = couponRepository.authenticatedUserCoupons(this.getConnectedUser().get().getId());
		boolean response = false;
		if (coupons != null) {
			for (Coupon couponTemp : coupons) {
				if (couponTemp.getCode().equals(code)) {
					response = true;
				}
				else {
					response = false;
				}
			}
		}
		return response;
	}


}

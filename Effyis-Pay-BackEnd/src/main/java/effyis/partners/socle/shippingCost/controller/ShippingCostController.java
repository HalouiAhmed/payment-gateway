package effyis.partners.socle.shippingCost.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import effyis.partners.socle.coupon.dto.CouponUpdateDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.shippingCost.dto.ShippingCostDTO;
import effyis.partners.socle.shippingCost.dto.response.ShippingCostResponseDTO;
import effyis.partners.socle.shippingCost.service.ShippingCostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/effyis/api/shippingCosts")
public class ShippingCostController {

	@Autowired
	ShippingCostService shippingCostService;

	@PostMapping
	@Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	CustomResponseDTO saveShippingCost(@RequestBody ShippingCostDTO shippingCostDTO) {
		return shippingCostService.saveShippingCost(shippingCostDTO);
	}

	@GetMapping(path = "/{name}")
	ShippingCostResponseDTO getShippingCost(@PathVariable("name") String name) {
		return shippingCostService.getShippingCost(name);
	}

	@GetMapping
	@Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	List<ShippingCostResponseDTO> getShippingCosts() {
		return shippingCostService.getAllShippingCosts();
	}

	@PutMapping
	@Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	CustomResponseDTO updateShippingCost(@RequestBody ShippingCostDTO shippingCostDTO) {
		return shippingCostService.updateShippingCost(shippingCostDTO);
	}
}

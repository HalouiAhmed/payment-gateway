package effyis.partners.socle.taxeRate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.shippingCost.dto.ShippingCostDTO;
import effyis.partners.socle.taxeRate.dto.TaxeRateDTO;
import effyis.partners.socle.taxeRate.dto.response.TaxeRateResponseDTO;
import effyis.partners.socle.taxeRate.service.TaxeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/effyis/api/taxes")
public class TaxeRateController {

	@Autowired
	TaxeRateService taxeRateService;

	@PostMapping(path = "/add")
	@Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	CustomResponseDTO saveTaxeRate(@RequestBody TaxeRateDTO taxeRateDTO) {
		return taxeRateService.saveTaxeRate(taxeRateDTO);
	}

	@GetMapping(path = "/{name}")
	TaxeRateResponseDTO getTaxeRate(@PathVariable("name") String name) {
		return taxeRateService.getTaxeRate(name);
	}

	@GetMapping
	@Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	List<TaxeRateResponseDTO> getTaxeRates() {
		return taxeRateService.getAllTaxeRates();
	}

	@PutMapping
	@Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	CustomResponseDTO updateTaxeRate(@RequestBody TaxeRateDTO taxeRateDTO) {
		return taxeRateService.updateTaxeRate(taxeRateDTO);
	}
	@GetMapping(path = "/activated")
	@Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	List<TaxeRateResponseDTO> getActivatedTaxRates(){
		return taxeRateService.getActivatedTaxeRates();
	}
}

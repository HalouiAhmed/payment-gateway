package effyis.partners.socle.taxeRate.service;

import java.util.List;

import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.shippingCost.dto.ShippingCostDTO;
import effyis.partners.socle.shippingCost.dto.response.ShippingCostResponseDTO;
import effyis.partners.socle.taxeRate.dto.TaxeRateDTO;
import effyis.partners.socle.taxeRate.dto.response.TaxeRateResponseDTO;

public interface TaxeRateService {

	CustomResponseDTO saveTaxeRate(TaxeRateDTO taxeRateDTO);
	List<TaxeRateResponseDTO> getAllTaxeRates();
    List<TaxeRateResponseDTO> getActivatedTaxeRates();
	TaxeRateResponseDTO getTaxeRate(String name);
	CustomResponseDTO updateTaxeRate(TaxeRateDTO taxeRateDTO);
	
}

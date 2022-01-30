package effyis.partners.socle.shippingCost.service;

import java.util.List;

import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.shippingCost.dto.ShippingCostDTO;
import effyis.partners.socle.shippingCost.dto.response.ShippingCostResponseDTO;

public interface ShippingCostService {

	CustomResponseDTO saveShippingCost(ShippingCostDTO shippingCostDto);
	List<ShippingCostResponseDTO> getAllShippingCosts();
	ShippingCostResponseDTO getShippingCost(String name);
	CustomResponseDTO updateShippingCost(ShippingCostDTO shippingCostDTO);

}

package effyis.partners.socle.shippingCost.service.implService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.shippingCost.dto.ShippingCostDTO;
import effyis.partners.socle.shippingCost.dto.response.ShippingCostResponseDTO;
import effyis.partners.socle.shippingCost.entity.ShippingCost;
import effyis.partners.socle.shippingCost.repository.ShippingCostRepository;
import effyis.partners.socle.shippingCost.service.ShippingCostService;

@Service
public class ShippingCostImplService implements ShippingCostService {

	@Autowired
	ModelMapper mapper;

	@Autowired
	ShippingCostRepository shippingCostRepository;
	
	@Autowired
	AccountRepository accountRepository;



	@Override
	public CustomResponseDTO saveShippingCost(ShippingCostDTO shippingCostDto) {
		ShippingCost shippingCost = new ShippingCost();
		CustomResponseDTO customResponseDTO = new CustomResponseDTO();
		mapper.map(shippingCostDto, shippingCost);
		Optional<Account> authenticatedUser = this.getConnectedUser();
		shippingCost.setAccount(authenticatedUser.get());
		shippingCostRepository.save(shippingCost);
		customResponseDTO.setResponse(shippingCost.getId().toString());
		return customResponseDTO;
	}

	@Override
	public ShippingCostResponseDTO getShippingCost(String name) {
		Optional<ShippingCost> shippingCost = shippingCostRepository.findByNameAndAccount(name,
				this.getConnectedUser().get().getId());
		ShippingCostResponseDTO shippingCostResponseDTO = new ShippingCostResponseDTO();
		if (shippingCost.isPresent()) {
			mapper.map(shippingCost.get(), shippingCostResponseDTO);
		} else {
			shippingCostResponseDTO = null;
		}
		return shippingCostResponseDTO;
	}

	@Override
	public List<ShippingCostResponseDTO> getAllShippingCosts() {
		List<ShippingCost> shippingCosts = shippingCostRepository
				.authenticatedUserShippingCosts(this.getConnectedUser().get().getId());
		List<ShippingCostResponseDTO> shippingCostResponseDTOS = new ArrayList<>();
		for (ShippingCost shippingCost : shippingCosts) {
			ShippingCostResponseDTO shippingCostResponseDTO = new ShippingCostResponseDTO();
			mapper.map(shippingCost, shippingCostResponseDTO);
			shippingCostResponseDTOS.add(shippingCostResponseDTO);
		}
		return shippingCostResponseDTOS;
	}

	@Override
	public CustomResponseDTO updateShippingCost(ShippingCostDTO shippingCostDTO) {
		CustomResponseDTO customResponseDTO = new CustomResponseDTO();
		Optional<ShippingCost> shippingCost = shippingCostRepository.findByNameAndAccount(shippingCostDTO.getName(),
				this.getConnectedUser().get().getId());
		if (shippingCost.isPresent()) {
			shippingCost.get().setActivated(shippingCostDTO.isActivated());
			shippingCostRepository.save(shippingCost.get());
			customResponseDTO.setResponse(shippingCost.get().getId().toString());
		} else {
			customResponseDTO.setResponse("no shippingCost with name " + shippingCostDTO.getName() + " existing");
		}

		return customResponseDTO;
	}

	public Optional<Account> getConnectedUser() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return accountRepository.findByLogin(email);
	}

	public boolean checkIfExisting(String name) {
		List<ShippingCost> shippingCosts = shippingCostRepository
				.authenticatedUserShippingCosts(this.getConnectedUser().get().getId());
		boolean response = false;
		if (shippingCosts != null) {
			for (ShippingCost shippingCostTemp : shippingCosts) {
				if (shippingCostTemp.getName().equals(name)) {
					response = true;
				} else {
					response = false;
				}
			}
		}
		return response;
	}

}

package effyis.partners.socle.taxeRate.service.implService;

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
import effyis.partners.socle.taxeRate.dto.TaxeRateDTO;
import effyis.partners.socle.taxeRate.dto.response.TaxeRateResponseDTO;
import effyis.partners.socle.taxeRate.entity.TaxeRate;
import effyis.partners.socle.taxeRate.repository.TaxeRateRepository;
import effyis.partners.socle.taxeRate.service.TaxeRateService;

@Service
public class TaxeRateImplService implements TaxeRateService {

	@Autowired
	ModelMapper mapper;

	@Autowired
	TaxeRateRepository taxeRateRepository;
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public CustomResponseDTO saveTaxeRate(TaxeRateDTO taxeRateDTO) {
		TaxeRate taxeRate = new TaxeRate();
		CustomResponseDTO customResponseDTO = new CustomResponseDTO();
		mapper.map(taxeRateDTO, taxeRate);
		Optional<Account> authenticatedUser = this.getConnectedUser();
		taxeRate.setAccount(authenticatedUser.get());
		taxeRateRepository.save(taxeRate);
		customResponseDTO.setResponse(taxeRate.getId().toString());	
		return customResponseDTO;
	}

	@Override
	public TaxeRateResponseDTO getTaxeRate(String name) {
		Optional<TaxeRate> taxeRate = taxeRateRepository.findByNameAndAccount(name, this.getConnectedUser().get().getId());
		TaxeRateResponseDTO taxeRateResponseDTO = new TaxeRateResponseDTO();
		if (taxeRate.isPresent()) {
			mapper.map(taxeRate.get(), taxeRateResponseDTO);
		} else {
			taxeRateResponseDTO = null;
		}
		return taxeRateResponseDTO;
	}

	@Override
	public List<TaxeRateResponseDTO> getAllTaxeRates() {
		List<TaxeRate> taxeRates = taxeRateRepository.authenticatedUserTaxes(this.getConnectedUser().get().getId());
		List<TaxeRateResponseDTO> taxeRateResponseDTOS = new ArrayList<>();
		for (TaxeRate taxeRate : taxeRates) {
			TaxeRateResponseDTO taxeRateResponseDTO = new TaxeRateResponseDTO();
			mapper.map(taxeRate, taxeRateResponseDTO);
			taxeRateResponseDTOS.add(taxeRateResponseDTO);
		}
		return taxeRateResponseDTOS;
	}


	@Override
	public CustomResponseDTO updateTaxeRate(TaxeRateDTO taxeRateDTO) {
		CustomResponseDTO customResponseDTO = new CustomResponseDTO();
		Optional<TaxeRate> taxeRate = taxeRateRepository.findByNameAndAccount(taxeRateDTO.getName(), this.getConnectedUser().get().getId());
		if (taxeRate.isPresent()) {
			taxeRate.get().setActivated(taxeRateDTO.isActivated());
			taxeRateRepository.save(taxeRate.get());
			customResponseDTO.setResponse(taxeRate.get().getId().toString());
		} else {
			customResponseDTO.setResponse("no taxe with name " + taxeRateDTO.getName() + " existing");
		}

		return customResponseDTO;
	}

	@Override
	public List<TaxeRateResponseDTO> getActivatedTaxeRates() {
		List<TaxeRateResponseDTO> taxeRateResponseDTOList = getAllTaxeRates();
		List<TaxeRateResponseDTO> responseDTOS = new ArrayList<>();
		for (TaxeRateResponseDTO tax:
			 taxeRateResponseDTOList) {
			if(tax.isActivated()){
				responseDTOS.add(tax);
			}
		}
		return responseDTOS;
	}

	public Optional<Account> getConnectedUser() {
		String email=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return accountRepository.findByLogin(email);
	}
	
	public boolean checkIfExisting(String name) {
		List<TaxeRate> taxes = taxeRateRepository.authenticatedUserTaxes(this.getConnectedUser().get().getId());
		boolean response = false;
		if (taxes != null) {
			for (TaxeRate taxeTemp : taxes) {
				if (taxeTemp.getName().equals(name)) {
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

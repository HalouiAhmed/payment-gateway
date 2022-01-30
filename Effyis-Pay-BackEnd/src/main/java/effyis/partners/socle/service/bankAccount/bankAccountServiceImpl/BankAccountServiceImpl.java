package effyis.partners.socle.service.bankAccount.bankAccountServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import effyis.partners.socle.coupon.dto.response.CouponResponseDTO;
import effyis.partners.socle.coupon.entity.Coupon;
import effyis.partners.socle.dto.bankAccount.BankAccountRequestDTO;
import effyis.partners.socle.dto.bankAccount.response.BankAccountResponseDTO;
import effyis.partners.socle.dto.company.response.CompanyResponseDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.bankAccount.BankAccount;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.bankAccount.BankAccountRepository;
import effyis.partners.socle.service.bankAccount.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	BankAccountRepository bankAccountRepository; 
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public List<BankAccountResponseDTO> getAllBankAccounts() {
			List<BankAccount> bankAccounts = bankAccountRepository.authenticatedUserBankAccounts(this.getConnectedUser().get().getId());
			List<BankAccountResponseDTO> bankAccountResponseDTOS = new ArrayList<>();
			for (BankAccount bankAccount : bankAccounts) {
				BankAccountResponseDTO bankAccountResponseDTO = new BankAccountResponseDTO();
				mapper.map(bankAccount, bankAccountResponseDTO);
				bankAccountResponseDTOS.add(bankAccountResponseDTO);
			}
			return bankAccountResponseDTOS;
		}


	@Override
	public CustomResponseDTO saveBankAccount(BankAccountRequestDTO bankAccountRequestDTO) {
		BankAccount bankAccount = new BankAccount();
		CustomResponseDTO customResponseDTO = new CustomResponseDTO();
		this.buildBankAccountObject(bankAccount, bankAccountRequestDTO);
		bankAccountRepository.save(bankAccount);
		customResponseDTO.setResponse(bankAccount.getId().toString());
		return customResponseDTO;
	}

	public Optional<Account> getConnectedUser() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return accountRepository.findByLogin(email);
	}

	BankAccount buildBankAccountObject(BankAccount bankAccount,BankAccountRequestDTO bankAccountRequestDTO) {
		bankAccount.setIban(bankAccountRequestDTO.getIban());
		bankAccount.setBankName(bankAccountRequestDTO.getBankName());
		bankAccount.setAccountCurrency(bankAccountRequestDTO.getAccountCurrency());
		bankAccount.setAccountCountry(bankAccountRequestDTO.getAccountCountry());
		bankAccount.setAccount(this.getConnectedUser().get());
		return bankAccount;
	}
}

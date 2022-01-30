package effyis.partners.socle.service.bankAccount;

import java.util.List;

import effyis.partners.socle.dto.bankAccount.BankAccountRequestDTO;
import effyis.partners.socle.dto.bankAccount.response.BankAccountResponseDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;

public interface BankAccountService {

	List<BankAccountResponseDTO> getAllBankAccounts();

	CustomResponseDTO saveBankAccount(BankAccountRequestDTO bankAccountRequestDTO);
}

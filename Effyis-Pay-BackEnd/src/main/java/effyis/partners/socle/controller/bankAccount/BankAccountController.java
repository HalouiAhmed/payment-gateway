package effyis.partners.socle.controller.bankAccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import effyis.partners.socle.dto.bankAccount.BankAccountRequestDTO;
import effyis.partners.socle.dto.bankAccount.response.BankAccountResponseDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.service.bankAccount.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/effyis/api/bankAccounts")
public class BankAccountController {

	@Autowired
	BankAccountService bankAccountService;

	@PostMapping
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	CustomResponseDTO saveBankAccount(@RequestBody BankAccountRequestDTO bankAccountRequestDTO) {
		return bankAccountService.saveBankAccount(bankAccountRequestDTO);
	}

	@GetMapping
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	List<BankAccountResponseDTO> getBankAccounts() {
		return bankAccountService.getAllBankAccounts();
	}
}

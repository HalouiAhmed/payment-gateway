package effyis.partners.socle.repository.bankAccount;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.bankAccount.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

	Optional<BankAccount> findByAccount(Account account);

	@Query(value = "SELECT * FROM bank_account where account_id = :idAccount", nativeQuery = true)
	List<BankAccount> authenticatedUserBankAccounts(@Param("idAccount") Long idAccount);

}

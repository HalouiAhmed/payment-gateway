package effyis.partners.socle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import effyis.partners.socle.entity.Account;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByLogin(String login);
}

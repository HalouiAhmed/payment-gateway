package effyis.partners.socle.taxeRate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.taxeRate.entity.TaxeRate;

public interface TaxeRateRepository extends JpaRepository<TaxeRate, Long> {
	
	Optional<TaxeRate> findByAccount(Account account);
	@Query(value = "SELECT * FROM taxe_rate where account_id = :idAccount", nativeQuery = true)
	List<TaxeRate> authenticatedUserTaxes(@Param("idAccount") Long idAccount); 
	@Query(value = "SELECT * FROM taxe_rate where name = :value and account_id = :idAccount", nativeQuery = true)
	Optional<TaxeRate> findByNameAndAccount(@Param("value") String name, @Param("idAccount") Long idAccount);


}

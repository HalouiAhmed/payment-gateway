package effyis.partners.socle.repository.company;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	Optional<Company> findByAccount(Account account);

	@Query(value = "SELECT * FROM company where account_id = :idAccount", nativeQuery = true)
	List<Company> authenticatedUserCompanies(@Param("idAccount") Long idAccount);


}

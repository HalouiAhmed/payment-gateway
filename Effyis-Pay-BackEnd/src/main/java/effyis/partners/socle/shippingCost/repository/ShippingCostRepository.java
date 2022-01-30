package effyis.partners.socle.shippingCost.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.shippingCost.entity.ShippingCost;

public interface ShippingCostRepository extends JpaRepository<ShippingCost, Long> {

	Optional<ShippingCost> findByAccount(Account account);
	@Query(value = "SELECT * FROM shipping_cost where account_id = :idAccount", nativeQuery = true)
	List<ShippingCost> authenticatedUserShippingCosts(@Param("idAccount") Long idAccount); 
	@Query(value = "SELECT * FROM shipping_cost where name = :value and account_id = :idAccount", nativeQuery = true)
	Optional<ShippingCost> findByNameAndAccount(@Param("value") String name, @Param("idAccount") Long idAccount);
}

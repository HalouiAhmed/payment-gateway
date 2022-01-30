package effyis.partners.socle.coupon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import effyis.partners.socle.coupon.entity.Coupon;
import effyis.partners.socle.entity.Account;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	
	Optional<Coupon> findByAccount(Account account);
	@Query(value = "SELECT * FROM coupon where account_id = :idAccount", nativeQuery = true)
	List<Coupon> authenticatedUserCoupons(@Param("idAccount") Long idAccount); 
	@Query(value = "SELECT * FROM coupon where code = :value and account_id = :idAccount", nativeQuery = true)
	Optional<Coupon> findByCodeAndAccount(@Param("value") String code, @Param("idAccount") Long idAccount);
	@Query(value = "SELECT * FROM coupon where id = :value and account_id = :idAccount", nativeQuery = true)
	Optional<Coupon> findByidAndAccount(@Param("value") Long id, @Param("idAccount") Long idAccount);

}
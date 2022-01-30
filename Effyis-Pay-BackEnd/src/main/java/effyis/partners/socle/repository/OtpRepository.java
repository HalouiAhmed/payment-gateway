package effyis.partners.socle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {
	Optional<Otp> findByAccount(Account  account);

}

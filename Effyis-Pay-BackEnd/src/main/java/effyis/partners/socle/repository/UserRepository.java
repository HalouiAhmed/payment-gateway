package effyis.partners.socle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
   Optional<User> findByAccount(Account account);
   Optional<User> findByAccountLogin(String login);
}

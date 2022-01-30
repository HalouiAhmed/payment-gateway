package effyis.partners.socle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.Customer;

public interface CustomersRepository extends JpaRepository<Customer, Long> {
 Optional<Customer> findById(long id);
 Optional<List<Customer>> findByAccount(Account account);

}

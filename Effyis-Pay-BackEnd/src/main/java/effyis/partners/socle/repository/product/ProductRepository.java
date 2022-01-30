package effyis.partners.socle.repository.product;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.product.Category;
import effyis.partners.socle.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author CHICHI Hamza
 */

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryAndAccount_Id(Category category,Long account_id);

    Optional<Product> findByIdAndAccount_Id(Long id, Long account_id);

    List<Product> findByAccount_Id(Long account_id);
}

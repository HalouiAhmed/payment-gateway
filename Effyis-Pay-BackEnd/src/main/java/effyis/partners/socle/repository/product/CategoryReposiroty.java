package effyis.partners.socle.repository.product;


import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.cloudinary.CloudinaryInformation;
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
public interface CategoryReposiroty extends JpaRepository<Category,Long> {
    Optional<Category> findByCategoryNameAndAccount_Id(String category,Long accout_id);
    Optional<Category> findByCategoryImage(CloudinaryInformation cloudinaryCategory);
    List<Category> findByAccount_Id(Long account_id);
    Optional<Category> findByIdAndAccount_Id(Long id, Long accout_id);
}

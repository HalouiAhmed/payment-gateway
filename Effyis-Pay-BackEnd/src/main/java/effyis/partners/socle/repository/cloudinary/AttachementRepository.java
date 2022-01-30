package effyis.partners.socle.repository.cloudinary;

import effyis.partners.socle.entity.cloudinary.Attachement;
import effyis.partners.socle.enums.TypeAttachement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MAHLA Ilyasse Badreddine
 *
 */
@Repository
public interface AttachementRepository extends JpaRepository<Attachement, Long> {

	Attachement findByTypeAttachement(TypeAttachement typeAttachement);

	@EntityGraph(value = "Attachement.cloudinaryInformationList", type = EntityGraphType.FETCH)
	Attachement findAttachementById(Long attachementId);

}

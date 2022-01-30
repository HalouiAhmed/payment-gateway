package effyis.partners.socle.repository.cloudinary;

import effyis.partners.socle.entity.cloudinary.Attachement;
import effyis.partners.socle.entity.cloudinary.CloudinaryInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
*
* @author MAHLA Ilyasse Badreddine
*
*/
@Repository
public interface CloudinaryInformationRepository extends JpaRepository<CloudinaryInformation, Long> {
	
	@Query(value = "SELECT * FROM cloudinary_information where attachement_id = :idAttachement", nativeQuery = true)
	List<CloudinaryInformation> getListByAttachementId(@Param("idAttachement") Long idAttachement);

	@Query(value = "SELECT COUNT(id) FROM cloudinary_information where attachement_id = :idAttachement", nativeQuery = true)
	long countInformationsByIdAttachement(@Param("idAttachement") Long idAttachement);

	Optional<CloudinaryInformation> findCloudinaryInformationByAttachement(Attachement attachement);

}

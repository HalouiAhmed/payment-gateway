package effyis.partners.socle.service.cloudinary;


import effyis.partners.socle.dto.response.AttachementResponseDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;

/**
 *
 * @author MAHLA Ilyasse Badreddine
 *
 */
public interface AttachementService {

	AttachementResponseDTO findAttachementById(Long idAttachement);
}

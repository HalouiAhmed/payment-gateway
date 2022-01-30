package effyis.partners.socle.service.cloudinary;


import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.enums.TypeAttachement;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 *
 * @author MAHLA Ilyasse Badreddine
 *
 */
public interface CloudinaryInformationService {

	public CustomResponseDTO uploadFile(MultipartFile file, String typeAttachement) throws IOException;
	public CustomResponseDTO changeFile(MultipartFile file, String typeAttachement, Long idInformation) throws IOException;
}

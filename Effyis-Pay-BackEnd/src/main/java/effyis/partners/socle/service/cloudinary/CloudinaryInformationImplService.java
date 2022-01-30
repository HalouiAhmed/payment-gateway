package effyis.partners.socle.service.cloudinary;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import effyis.partners.socle.Util.factory.CloudinaryInformationServiceFactory;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.enums.TypeAttachement;
import effyis.partners.socle.repository.cloudinary.AttachementRepository;
import effyis.partners.socle.repository.cloudinary.CloudinaryInformationRepository;

/**
 *
 * @author MAHLA Ilyasse Badreddine
 *
 */
@Service
public class CloudinaryInformationImplService implements CloudinaryInformationService {

	@Autowired
	CloudinaryInformationRepository cloudinaryInformationRepository;

	@Autowired
	AttachementRepository attachementRepository;
	
	@Autowired
	CloudinaryInformationServiceFactory factory;
	

	Cloudinary cloudinary;
	
	@Override
	public CustomResponseDTO uploadFile(MultipartFile file, String typeAttachement) throws IOException {
		return factory.uploadFactoryMethod(file, typeAttachement); // call the factory method to process according to attachement type
	}

	@Override
	public CustomResponseDTO changeFile(MultipartFile file, String typeAttachement, Long idInformation) throws IOException {
		CustomResponseDTO responseDTO = new CustomResponseDTO();
		for(TypeAttachement typeAttachementValue : TypeAttachement.values()) {
			if(typeAttachementValue.toString().equalsIgnoreCase(typeAttachement)) {
				if(cloudinaryInformationRepository.getListByAttachementId(attachementRepository.
						findByTypeAttachement(typeAttachementValue).getId()).isEmpty()) {
					responseDTO.setResponse("No file is already uploaded in order to be changed");
				}
				else {
					cloudinary.uploader().destroy(cloudinaryInformationRepository.findById(idInformation).get().getPublic_id(), ObjectUtils.emptyMap());
					cloudinaryInformationRepository.deleteById(idInformation);
					responseDTO = this.uploadFile(file, typeAttachement);
				}
			}
		}
		return responseDTO;
	}

}

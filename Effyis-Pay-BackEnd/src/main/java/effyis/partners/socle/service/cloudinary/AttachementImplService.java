package effyis.partners.socle.service.cloudinary;


import effyis.partners.socle.dto.response.AttachementResponseDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.entity.cloudinary.Attachement;
import effyis.partners.socle.entity.cloudinary.CloudinaryInformation;
import effyis.partners.socle.enums.TypeAttachement;
import effyis.partners.socle.repository.cloudinary.AttachementRepository;
import effyis.partners.socle.repository.cloudinary.CloudinaryInformationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MAHLA Ilyasse Badreddine
 *
 */
@Service
public class AttachementImplService implements AttachementService {

	@Autowired
	AttachementRepository attachementRepository;
	
	@Autowired
	CloudinaryInformationRepository cloudinaryInformationRepository;

	@Value("${config.datasources[0].client}")
	String client;
	
	@Override
	public AttachementResponseDTO findAttachementById(Long idAttachement) {
		AttachementResponseDTO attachementResponseDTO = new AttachementResponseDTO();
		if (attachementRepository.findAttachementById(idAttachement).getCloudinaryInformationList().isEmpty()) {
			attachementResponseDTO.setStoreName(client);
			return attachementResponseDTO;
		} else {
			Attachement attachement = attachementRepository.findAttachementById(idAttachement);
			ModelMapper mapper = new ModelMapper();
			attachementResponseDTO = mapper.map(attachement, AttachementResponseDTO.class);
			return attachementResponseDTO;
		}
	}
}

package effyis.partners.socle.Util.factory;


import effyis.partners.socle.dto.CustomCompanyDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryInformationServiceFactory {

	public CustomResponseDTO uploadFactoryMethod(MultipartFile multipartfile, String attachementType) throws IOException;

}

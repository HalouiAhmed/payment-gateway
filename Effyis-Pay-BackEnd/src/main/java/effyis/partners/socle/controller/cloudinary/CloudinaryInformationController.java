package effyis.partners.socle.controller.cloudinary;

import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.service.cloudinary.CloudinaryInformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 *
 * @author MAHLA Ilyasse Badreddine
 *
 */

@RestController
@RequestMapping(path = "/effyis/api/cloudinary")
public class CloudinaryInformationController {

	@Autowired
	CloudinaryInformationService cloudinaryInformationService;

	@PostMapping(path = "/upload/{typeAttachement}")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public CustomResponseDTO uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String typeAttachement)
			throws IOException {
		return cloudinaryInformationService.uploadFile(file, typeAttachement);
	}

}

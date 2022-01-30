package effyis.partners.socle.Util.factory.factoryImpl;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import effyis.partners.socle.Util.CloudinaryUtil;
import effyis.partners.socle.Util.FileConverter;
import effyis.partners.socle.Util.FileSizeCalculator;
import effyis.partners.socle.Util.factory.CloudinaryInformationServiceFactory;
import effyis.partners.socle.dto.CustomCompanyDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.entity.cloudinary.Attachement;
import effyis.partners.socle.entity.cloudinary.CloudinaryInformation;
import effyis.partners.socle.enums.TypeAttachement;
import effyis.partners.socle.repository.cloudinary.AttachementRepository;
import effyis.partners.socle.repository.cloudinary.CloudinaryInformationRepository;

@Component
public class CloudinaryInformationServiceImplFactory implements effyis.partners.socle.Util.factory.CloudinaryInformationServiceFactory, effyis.partners.socle.Util.CloudinaryUtil {

    @Autowired
    CloudinaryInformationRepository cloudinaryInformationRepository;

    @Autowired
    AttachementRepository attachementRepository;

    @Autowired
    Cloudinary cloudinary;

    @Value("${attachementSize.logo}")
    private double logoSize;

    @Value("${attachementSize.product}")
    private double productSize;

    @Value("${attachementSize.category}")
    private double categorySize;

    @Override
    public CustomResponseDTO uploadFactoryMethod(MultipartFile multipartfile, String attachementType) throws IOException {
        List<String> imageTypes = new ArrayList<>();
        imageTypes.add("image/png");
        imageTypes.add("image/jpg");
        imageTypes.add("image/jpeg");
        CustomResponseDTO urlResponseDTO = new CustomResponseDTO();
        String message = null;
        File uploadedFile = FileConverter.convertMultiPartToFile(multipartfile); // convert MultipartFile to File


        if (TypeAttachement.PRODUCT.toString().equalsIgnoreCase(attachementType)) {   // slideshow typeAttachement Case process
            if (FileSizeCalculator.filesizeInMegaBytes(uploadedFile) <= productSize
                    && imageTypes.contains(multipartfile.getContentType())) {
                Map uploadResult = uploadGeneric(uploadedFile, cloudinary);    // upload to Cloudinary
                FileConverter.cleanTemporary(uploadedFile); // Delete File from project

                CloudinaryInformation cloudinaryInformation = new CloudinaryInformation();
                setCloudinaryInfoGeneric(uploadResult, cloudinaryInformation);	/* Create a new CloudinaryInformation object
				 																	and set the data from Cloudinary to the object */
                Attachement attachement = attachementRepository.findByTypeAttachement(TypeAttachement.PRODUCT);
                Long idAttachementTemp = attachementRepository.findByTypeAttachement(TypeAttachement.PRODUCT).getId();
                cloudinaryInformation.setAttachement(attachement); // Set Parent Child Foreign key relationship
                cloudinaryInformationRepository.save(cloudinaryInformation); // save into DataBase
                urlResponseDTO.setResponse(cloudinaryInformation.getId().toString());
            } else {
                if (FileSizeCalculator.filesizeInMegaBytes(uploadedFile) > productSize) { // File size not respected process
                    urlResponseDTO.setResponse("Veillez selectionnner un fichier de taille maximal " + productSize + " Mo");
                    FileConverter.cleanTemporary(uploadedFile);
                }
                if (!imageTypes.contains(multipartfile.getContentType())) { // File extension not respected process
                    urlResponseDTO.setResponse("Veillez selectionnner un fichier de format png, jpg, ou jpeg");
                    FileConverter.cleanTemporary(uploadedFile);
                }
            }
        } else if (TypeAttachement.CATEGORY.toString().equalsIgnoreCase(attachementType)) {   // slideshow typeAttachement Case process
            if (FileSizeCalculator.filesizeInMegaBytes(uploadedFile) <= categorySize
                    && imageTypes.contains(multipartfile.getContentType())) {
                Map uploadResult = uploadGeneric(uploadedFile, cloudinary);    // upload to Cloudinary
                FileConverter.cleanTemporary(uploadedFile); // Delete File from project

                CloudinaryInformation cloudinaryInformation = new CloudinaryInformation();
                setCloudinaryInfoGeneric(uploadResult, cloudinaryInformation);	/* Create a new CloudinaryInformation object
				 																	and set the data from Cloudinary to the object */
                Attachement attachement = attachementRepository.findByTypeAttachement(TypeAttachement.CATEGORY);
                Long idAttachementTemp = attachementRepository.findByTypeAttachement(TypeAttachement.CATEGORY).getId();
                cloudinaryInformation.setAttachement(attachement); // Set Parent Child Foreign key relationship
                cloudinaryInformationRepository.save(cloudinaryInformation); // save into DataBase
                urlResponseDTO.setResponse(cloudinaryInformation.getId().toString());
            } else {
                if (FileSizeCalculator.filesizeInMegaBytes(uploadedFile) > categorySize) { // File size not respected process
                    urlResponseDTO.setResponse("Veillez selectionnner un fichier de taille maximal " + categorySize + " Mo");
                    FileConverter.cleanTemporary(uploadedFile);
                }
                if (!imageTypes.contains(multipartfile.getContentType())) { // File extension not respected process
                    urlResponseDTO.setResponse("Veillez selectionnner un fichier de format png, jpg, ou jpeg");
                    FileConverter.cleanTemporary(uploadedFile);
                }
            }
        } else {
            urlResponseDTO.setResponse("Veillez selectionnner un type d'attachement valide");
            FileConverter.cleanTemporary(uploadedFile);
        }

        return urlResponseDTO;
    }
    
	
}

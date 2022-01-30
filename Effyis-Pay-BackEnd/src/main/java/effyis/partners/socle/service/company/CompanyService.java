package effyis.partners.socle.service.company;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import effyis.partners.socle.dto.company.CompanyRequestDTO;
import effyis.partners.socle.dto.company.response.CompanyResponseDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;

public interface CompanyService {


	List<CompanyResponseDTO> getAllCompanies();

	CustomResponseDTO saveCompany(MultipartFile multipartfile, CompanyRequestDTO companyRequestDTO) throws IOException;

	CustomResponseDTO updateCompany(MultipartFile multipartfile, CompanyResponseDTO companyResponseDTO)
			throws IOException;
}

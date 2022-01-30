package effyis.partners.socle.controller.company;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import effyis.partners.socle.dto.company.CompanyRequestDTO;
import effyis.partners.socle.dto.company.response.CompanyResponseDTO;
import effyis.partners.socle.dto.response.CustomResponseDTO;
import effyis.partners.socle.service.company.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/effyis/api/companies")
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	@PostMapping
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	CustomResponseDTO saveCompany(@RequestParam("file") MultipartFile file, @RequestPart("company") CompanyRequestDTO companyRequestDTO) throws IOException {
		return companyService.saveCompany(file, companyRequestDTO);
	}
	
	@PutMapping
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	CustomResponseDTO updateCompany(@RequestParam("file") MultipartFile file, @RequestPart("company") CompanyResponseDTO companyResponseDTO) throws IOException {
		return companyService.updateCompany(file, companyResponseDTO);
	}
	
	
	@GetMapping
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	List<CompanyResponseDTO> getCompanies() {
		return companyService.getAllCompanies();
	}


}

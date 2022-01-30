package effyis.partners.socle.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import effyis.partners.socle.Util.response.MessageCSVResponse;
import effyis.partners.socle.dto.CustomerDTO;
import effyis.partners.socle.service.CustomerService;


@RestController
@RequestMapping("/effyis/api/costumer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
	@GetMapping("/{id}")
	public CustomerDTO getUser(@PathVariable long id) {
		
		return customerService.getCustomer(id);

	}
	@GetMapping
	public List<CustomerDTO> getListCosumers(){
		return customerService.getAllCustomer();
	}
	
	@PostMapping
	public CustomerDTO CreateUser(@RequestBody CustomerDTO customerDTO) {
        
		return customerService.createCustomer(customerDTO);

	}
	@PutMapping("/{id}")
	public CustomerDTO UpdateUser(@PathVariable long id , @RequestBody CustomerDTO customerDTO) {
		return customerService.updateCustomer(id, customerDTO);
	}
	@DeleteMapping("/{id}")
	public CustomerDTO DeleteUser(@PathVariable long id) {
		return customerService.deleteCustomer(id);
	}

	@PostMapping("/upload")
	public ResponseEntity<MessageCSVResponse> uploadCustomerCSV(@RequestParam("file") MultipartFile file){
		return customerService.uploadCustomerCSV(file);
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> getFile() {
		String filename = "custumers.csv";
		InputStreamResource file = new InputStreamResource(customerService.downloadCustomerCSV());

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv"))
				.body(file);
	}
}

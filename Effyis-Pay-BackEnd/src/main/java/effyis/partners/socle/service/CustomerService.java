package effyis.partners.socle.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import effyis.partners.socle.Util.response.MessageCSVResponse;
import effyis.partners.socle.dto.CustomerDTO;

public interface CustomerService {
 
	public CustomerDTO createCustomer(CustomerDTO customerDTO);
	public CustomerDTO updateCustomer(Long id,CustomerDTO customerDTO);
	public CustomerDTO getCustomer(long id);
	public CustomerDTO deleteCustomer(long id);
	public List<CustomerDTO> getAllCustomer();
	ResponseEntity<MessageCSVResponse> uploadCustomerCSV(MultipartFile file);
	ByteArrayInputStream downloadCustomerCSV();
}

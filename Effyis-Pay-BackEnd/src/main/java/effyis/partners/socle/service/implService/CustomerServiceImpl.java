package effyis.partners.socle.service.implService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import effyis.partners.socle.Util.CSVHelper.CSVHelper;
import effyis.partners.socle.Util.response.MessageCSVResponse;
import effyis.partners.socle.dto.CustomerDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.Customer;
import effyis.partners.socle.exception.ElementNotFoundException;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.CustomersRepository;
import effyis.partners.socle.service.AccountService;
import effyis.partners.socle.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private AccountRepository accountRespository;
    @Autowired
    private AccountService accountService;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        String email = accountService.getConnectedUser();
        Account account = accountRespository.findByLogin(email).orElse(null);
        if (account == null) throw new ElementNotFoundException("account");
        ModelMapper modelMapper = new ModelMapper();
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setAccount(account);
        customersRepository.save(customer);
        return customerDTO;
    }


    @Override
    public CustomerDTO getCustomer(long id) {
        Customer customer = customersRepository.findById(id).orElse(null);
        if (customer == null) throw new ElementNotFoundException("costumer");
        ModelMapper modelMapper = new ModelMapper();
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        return customerDTO;
    }

    @Override
    public CustomerDTO deleteCustomer(long id) {
        Customer customer = customersRepository.findById(id).orElse(null);
        if (customer == null) throw new ElementNotFoundException("costumer");
        customersRepository.delete(customer);
        return null;
    }


    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customersRepository.findById(id).orElse(null);
        if (customer == null) throw new ElementNotFoundException("costumer");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(customerDTO, customer);
        customer.setId(id);
        System.err.println(id);
        customersRepository.save(customer);
        return customerDTO;
    }


    @Override
    public List<CustomerDTO> getAllCustomer() {
        String email = accountService.getConnectedUser();
        Account account = accountRespository.findByLogin(email).orElse(null);
        if (account == null) throw new ElementNotFoundException("account");
        List<Customer> customers = customersRepository.findByAccount(account).orElse(null);
        if (customers == null) throw new ElementNotFoundException("list customers");
        ModelMapper modelMapper = new ModelMapper();
        List<CustomerDTO> customerDTOs = modelMapper.map(customers, new TypeToken<List<CustomerDTO>>() {
        }.getType());


        return customerDTOs;
    }

    @Override
    public ResponseEntity<MessageCSVResponse> uploadCustomerCSV(MultipartFile file) {
        String email = accountService.getConnectedUser();
        Account account = accountRespository.findByLogin(email).orElse(null);
        String message = "";
        if (1==1) {
            try {
                List<Customer> customers = CSVHelper.csvToCustumer(file.getInputStream());
                customers.forEach(customer -> {customer.setAccount(account);});
                customersRepository.saveAll(customers);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new MessageCSVResponse(message));
            } catch (IOException e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
        }
        else {
            message = "Please upload a csv file!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageCSVResponse(message));
        }
    }

    @Override
    public ByteArrayInputStream downloadCustomerCSV() {
        String email = accountService.getConnectedUser();
        Account account = accountRespository.findByLogin(email).orElse(null);
        List<Customer> customers = customersRepository.findByAccount(account).orElseThrow(
                ()-> new ElementNotFoundException("custumers")
        );

        ByteArrayInputStream in = CSVHelper.custumersToCSV(customers);
        return in;
    }


}

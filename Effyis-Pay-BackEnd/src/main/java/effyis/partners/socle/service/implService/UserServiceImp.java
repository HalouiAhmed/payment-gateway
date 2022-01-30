package effyis.partners.socle.service.implService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import effyis.partners.socle.dto.AccountDTO;
import effyis.partners.socle.dto.UserDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.User;
import effyis.partners.socle.exception.CustomAuthenticationException;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.RoleRepository;
import effyis.partners.socle.repository.UserRepository;
import effyis.partners.socle.service.AccountService;
import effyis.partners.socle.service.UserService;
@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AccountService accountService;
    
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		if(userDTO.isAgreeToTermes()!=true) {
            throw new CustomAuthenticationException("Please agree to the terms and conditions");
		}
		ModelMapper modelMapper = new ModelMapper();
		User user=modelMapper.map(userDTO,User.class);
		Account account =new Account(userDTO.getEmail(),bCryptPasswordEncoder.encode(userDTO.getPassword()),userDTO.getCompanyName());
		user.setAccount(account);
		account.setRole(null);
		User newUser=userRepository.save(user);
		UserDTO userToShow=modelMapper.map(newUser, UserDTO.class);
		return userToShow;
	}
	
	@Override
	public AccountDTO getAuthenticatedUserAccount() {
				AccountDTO accountDTO = new AccountDTO();
				System.out.println("login: " + accountService.getConnectedUser());
				return accountDTO;
	}


}

package effyis.partners.socle.service.implService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import effyis.partners.socle.configuration.security.JWTProvider;
import effyis.partners.socle.dto.AccountDTO;
import effyis.partners.socle.dto.AuthenticatedUserDataRequestDTO;
import effyis.partners.socle.dto.AuthenticatedUserFullCredentialsDTO;
import effyis.partners.socle.dto.AuthenticationDTO;
import effyis.partners.socle.dto.JwtDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.User;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.UserRepository;
import effyis.partners.socle.service.AccountService;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Service
public class AccountImplService implements AccountService {
     @Autowired
     private  UserRepository userRepository;
		
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AuthenticationManager authenticationManger;

	@Value("${security.jwt.secret}")
	private String secret;

	@Value("${security.jwt.expiration.time}")
	private long expirationTime;

	@Value("${default.role}")
	private String defaultRole;


	@Override
	public Optional<Account> findByLogin(String login) {
		return this.accountRepository.findByLogin(login);
	}

	@Override
	public List<AccountDTO> getAccounts() {
		return this.accountRepository.findAll().stream().map(account -> this.modelMapper.map(account, AccountDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public JwtDTO authenticateUser(AuthenticationDTO authenticationDTO) {
		this.authenticationManger.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getPassword()));
		Account account = this.findByLogin(authenticationDTO.getLogin()).orElse(null);
		String jwt = JWTProvider.generateJWT(authenticationDTO.getLogin(),
				((account != null) && (account.getRole() != null)) ? account.getRole().getRole() : this.defaultRole,
				this.secret, this.expirationTime,((account != null) && (account.isVerifiedEmail()!= null)) ? account.isVerifiedEmail() :false);
		JwtDTO jwtDTO = new JwtDTO();
		User user =userRepository.findByAccount(account).orElse(null);
		jwtDTO.setFullName(user.getFullName());
		jwtDTO.setJwt(jwt);
		return jwtDTO;
	}
	@Override
	public String getConnectedUser() {
		String email=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return email;
	}

	@Override
	public AuthenticatedUserFullCredentialsDTO getAuthenticatedUserAccount() {
		// TODO Auto-generated method stub
		AuthenticatedUserFullCredentialsDTO authenticatedUserDTO = new AuthenticatedUserFullCredentialsDTO();
		Optional<Account> account = accountRepository.findByLogin(this.getConnectedUser());
		modelMapper.map(account.get(), authenticatedUserDTO);
		Optional<User>  authenticatedUserData = userRepository.findByAccount(account.get());
		this.buildAuthenticatedUserDTOObject(authenticatedUserDTO, authenticatedUserData.get());
		return authenticatedUserDTO;
	}
	
	public void buildAuthenticatedUserDTOObject(AuthenticatedUserFullCredentialsDTO authenticatedUserDTO, User userData) {
		authenticatedUserDTO.setAdress(userData.getAdress());
		authenticatedUserDTO.setPhoneNumber(userData.getPhoneNumber());
		authenticatedUserDTO.setCountry(userData.getCountry());
		authenticatedUserDTO.setFullName(userData.getFullName());
		authenticatedUserDTO.setPostalCode(userData.getPostalCode());
		authenticatedUserDTO.setCity(userData.getCity());
		authenticatedUserDTO.setBirthDate(userData.getBirthDate());
	}
	
	public Long saveAuthenticatedUserData(AuthenticatedUserDataRequestDTO userDataRequestDTO) {
		Optional<Account> account = accountRepository.findByLogin(this.getConnectedUser());
		Optional<User> userData = userRepository.findByAccount(account.get());
		modelMapper.map(userDataRequestDTO, userData.get());
		userData.get().setBirthDate(userDataRequestDTO.getBirthDate());
		userRepository.save(userData.get());
		return userData.get().getId();
	}
	
	
}

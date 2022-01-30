package effyis.partners.socle.service;

import java.util.List;
import java.util.Optional;

import effyis.partners.socle.dto.AccountDTO;
import effyis.partners.socle.dto.AuthenticatedUserDataRequestDTO;
import effyis.partners.socle.dto.AuthenticatedUserFullCredentialsDTO;
import effyis.partners.socle.dto.AuthenticationDTO;
import effyis.partners.socle.dto.JwtDTO;
import effyis.partners.socle.entity.Account;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public interface AccountService {

	Optional<Account> findByLogin(String login);

	JwtDTO authenticateUser(AuthenticationDTO authenticationDTO) throws Exception;

	List<AccountDTO> getAccounts();

	String getConnectedUser();
	
	AuthenticatedUserFullCredentialsDTO getAuthenticatedUserAccount();
	
	Long saveAuthenticatedUserData(AuthenticatedUserDataRequestDTO userDataRequestDTO);
	
}

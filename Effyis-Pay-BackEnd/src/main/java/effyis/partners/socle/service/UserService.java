package effyis.partners.socle.service;

import effyis.partners.socle.dto.AccountDTO;
import effyis.partners.socle.dto.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO userDTO);
	
	AccountDTO getAuthenticatedUserAccount();

}

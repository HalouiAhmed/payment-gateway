package effyis.partners.socle.service;

import javax.mail.MessagingException;

import effyis.partners.socle.dto.PasswordDTO;

public interface OtpService {
  
	void  sendEMailAndSaveOtp() throws MessagingException;
	void verifiedOtp(PasswordDTO passwordDTO);
	void checkMailAndsendEMailAndSaveOtp(PasswordDTO paswwordDTO)throws MessagingException;
	void verifiedOtpAndSetPassword(PasswordDTO paswwordDTO);
}

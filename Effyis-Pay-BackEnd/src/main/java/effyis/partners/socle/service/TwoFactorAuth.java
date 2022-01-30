package effyis.partners.socle.service;

import effyis.partners.socle.dto.AuthenticationDTO;
import effyis.partners.socle.dto.JwtDTO;
import effyis.partners.socle.dto.TwoFactorAuthDTO;
import effyis.partners.socle.dto.UserDTO;
import effyis.partners.socle.entity.User;

import javax.mail.MessagingException;

public interface TwoFactorAuth {

    void sendOtpForTwoStepAuth(AuthenticationDTO authenticationDTO) throws MessagingException;
    void verifyCredential(AuthenticationDTO authenticationDTO) throws MessagingException;
    JwtDTO twoStepsVerification(TwoFactorAuthDTO twoFactorAuthDTO);
}

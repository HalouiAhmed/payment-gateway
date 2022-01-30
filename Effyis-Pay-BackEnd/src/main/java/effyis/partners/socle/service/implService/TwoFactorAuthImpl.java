package effyis.partners.socle.service.implService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import effyis.partners.socle.Util.Outils;
import effyis.partners.socle.dto.AuthenticationDTO;
import effyis.partners.socle.dto.JwtDTO;
import effyis.partners.socle.dto.MailDTO;
import effyis.partners.socle.dto.TwoFactorAuthDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.Otp;
import effyis.partners.socle.entity.User;
import effyis.partners.socle.exception.CustomAuthenticationException;
import effyis.partners.socle.exception.ElementNotFoundException;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.OtpRepository;
import effyis.partners.socle.repository.RoleRepository;
import effyis.partners.socle.repository.UserRepository;
import effyis.partners.socle.service.MailService;
import effyis.partners.socle.service.TwoFactorAuth;

@Service
public class TwoFactorAuthImpl implements TwoFactorAuth {

    @Autowired
    MailService mailService;
    @Autowired
    Outils utils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AccountImplService accountImplService;

    @Value("${custom.mailFrom}")
    private String mailFrom;

    private User checkLoginAndPassword(AuthenticationDTO authenticationDTO) {

        User user = userRepository.findByAccountLogin(authenticationDTO.getLogin()).orElseThrow(
                () -> new ElementNotFoundException("account"));

        if (!bCryptPasswordEncoder.matches(authenticationDTO.getPassword(), user.getAccount().getPassword())) {
            throw new CustomAuthenticationException("password invalid");
        }

        return user;
    }

    @Override
    public void verifyCredential(AuthenticationDTO authenticationDTO) throws MessagingException {

        User user = checkLoginAndPassword(authenticationDTO);

        /*if(user.getAccount().isVerifiedEmail()==false){
            throw new CustomAuthenticationException("You must verify your email address first");
        }*/
        sendOtpForTwoStepAuth(authenticationDTO);
    }

    @Override
    public void sendOtpForTwoStepAuth(AuthenticationDTO authenticationDTO) throws MessagingException {

        String email = authenticationDTO.getLogin();
        String generatedOtp = utils.generateOtp(6);
        Account accountFromDb = accountRepository.findByLogin(email).orElse(null);
        if (accountFromDb == null) throw new ElementNotFoundException("account");
        LocalDateTime date = LocalDateTime.now();
        Otp otpFromDb = otpRepository.findByAccount(accountFromDb).orElse(new Otp());
        otpFromDb.setAccount(accountFromDb);
        otpFromDb.setDate(date);
        otpFromDb.setOtp(bCryptPasswordEncoder.encode(generatedOtp));
        otpRepository.save(otpFromDb);

        MailDTO mailDto = new MailDTO();

        mailDto.setFrom(mailFrom);
        mailDto.setTo(email);
        mailDto.setSubject("Two Step Verification");
        mailDto.setTemplate("twostepauth");
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("otp", generatedOtp);
        mailDto.setProps(props);
        mailService.sendMail(mailDto);
    }

    @Override
    public JwtDTO twoStepsVerification(TwoFactorAuthDTO twoFactorAuthDTO) {
        User user = userRepository.findByAccountLogin(twoFactorAuthDTO.getLogin()).orElseThrow(
                () -> new ElementNotFoundException("account"));
        Account accountFromDb = accountRepository.findByLogin(twoFactorAuthDTO.getLogin()).orElseThrow(
                () -> new ElementNotFoundException("account"));
        Otp otpFromDb = otpRepository.findByAccount(user.getAccount()).get();

        if (bCryptPasswordEncoder.matches((twoFactorAuthDTO.getOtp()), otpFromDb.getOtp()) & !(LocalDateTime.now().isAfter(otpFromDb.getDate().plusMinutes(3)))) {
            AuthenticationDTO authenticationDTO = new AuthenticationDTO();
            authenticationDTO.setLogin(twoFactorAuthDTO.getLogin());
            authenticationDTO.setPassword(twoFactorAuthDTO.getPassword());
            accountFromDb.setVerifiedEmail(true);
            accountRepository.save(accountFromDb);
            accountFromDb.setRole(roleRepository.findById(1L).get());
            accountRepository.save(accountFromDb);
            return accountImplService.authenticateUser(authenticationDTO);
        }

        return null;
    }
}

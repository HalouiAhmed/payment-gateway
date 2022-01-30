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
import effyis.partners.socle.dto.MailDTO;
import effyis.partners.socle.dto.PasswordDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.Otp;
import effyis.partners.socle.exception.ElementNotFoundException;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.repository.OtpRepository;
import effyis.partners.socle.repository.RoleRepository;
import effyis.partners.socle.service.AccountService;
import effyis.partners.socle.service.MailService;
import effyis.partners.socle.service.OtpService;

@Service
public class OtpServiceImpl implements OtpService {
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    Outils utils;
    @Autowired
    MailService mailService;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${custom.mailFrom}")
    private String mailFrom;

    @Override
    public void sendEMailAndSaveOtp() throws MessagingException {
        String email = accountService.getConnectedUser();
        String aleaOtp = utils.generateOtp(6);
        Account accountFromDb = accountRepository.findByLogin(email).orElse(null);
        if (accountFromDb == null) throw new ElementNotFoundException("account");
        LocalDateTime date = LocalDateTime.now();
        Otp otpFromDb = otpRepository.findByAccount(accountFromDb).orElse(new Otp());
        otpFromDb.setAccount(accountFromDb);
        otpFromDb.setDate(date);
        otpFromDb.setOtp(bCryptPasswordEncoder.encode(aleaOtp));
        otpRepository.save(otpFromDb);

        MailDTO mailDto = new MailDTO();

        mailDto.setFrom(mailFrom);
        mailDto.setTo(email);
        mailDto.setSubject("Validation de mail");
        mailDto.setTemplate("hello");
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("otp", aleaOtp);
        mailDto.setProps(props);
        mailService.sendMail(mailDto);

    }

    @Override
    public void verifiedOtp(PasswordDTO passwordDTO) {
        String email = accountService.getConnectedUser();
        Account accountFromDb = accountRepository.findByLogin(email).get();
        Otp otpFromDb = otpRepository.findByAccount(accountFromDb).get();
        System.err.println(roleRepository.findById(1L).get());
        System.err.println("Account : "+ accountFromDb.getRole().getRole());
        if (this.bCryptPasswordEncoder.matches(passwordDTO.getOtp(), otpFromDb.getOtp()) & !(LocalDateTime.now().isAfter(otpFromDb.getDate().plusMinutes(10)))) {
            accountFromDb.setVerifiedEmail(true);

            accountFromDb.setRole(roleRepository.findById(1L).get());
            accountRepository.save(accountFromDb);
        } else throw new ElementNotFoundException("otp doese not mache or date expired");

    }

    @Override
    public void checkMailAndsendEMailAndSaveOtp(PasswordDTO passwordDTO) throws MessagingException {
        Account account = accountRepository
                .findByLogin(passwordDTO.getEmail())
                .orElseThrow(() -> new ElementNotFoundException("account"));
        LocalDateTime date = LocalDateTime.now();
        String aleaOtp = utils.generateOtp(6);
        Otp otpFromDb = otpRepository.findByAccount(account).orElse(new Otp());
        otpFromDb.setAccount(account);
        otpFromDb.setDate(date);
        otpFromDb.setOtp(bCryptPasswordEncoder.encode(aleaOtp));
        otpRepository.save(otpFromDb);
        MailDTO mailDto = new MailDTO();
        mailDto.setFrom(mailFrom);
        mailDto.setTo(passwordDTO.getEmail());
        mailDto.setSubject("Reinitialisation du mot de passe");
        mailDto.setTemplate("hello");
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("otp", aleaOtp);
        mailDto.setProps(props);
        mailService.sendMail(mailDto);
    }

    @Override
    public void verifiedOtpAndSetPassword(PasswordDTO passwordDTO) {
        Account account = accountRepository
                .findByLogin(passwordDTO.getEmail())
                .orElseThrow(() -> new ElementNotFoundException("account"));
        Otp otpFromDb = otpRepository
                .findByAccount(account)
                .orElseThrow(() -> new ElementNotFoundException("otp"));
        if (this.bCryptPasswordEncoder.matches(passwordDTO.getOtp(), otpFromDb.getOtp()) & !(LocalDateTime.now().isAfter(otpFromDb.getDate().plusMinutes(10)))) {
            if (passwordDTO.getPassword() != null) {
                account.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getPassword()));
                accountRepository.save(account);
            }

        } else throw new ElementNotFoundException("otp doese not mache or date expired");


    }

}



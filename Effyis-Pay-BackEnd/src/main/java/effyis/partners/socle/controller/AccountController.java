package effyis.partners.socle.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import effyis.partners.socle.dto.*;
import effyis.partners.socle.service.TwoFactorAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.service.AccountService;
import effyis.partners.socle.service.OtpService;
import effyis.partners.socle.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * @author EL KOTB ZAKARIA
 */
@RestController
@RequestMapping("/effyis/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountRepository rep;
    @Autowired
    private OtpService otpService;
    @Autowired
    TwoFactorAuth twoFactorAuth;

    @PostMapping("/login")
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
    public JwtDTO authenticate(@RequestBody AuthenticationDTO authenticationDTO) throws Exception {
        return this.accountService.authenticateUser(authenticationDTO);
    }

    @PostMapping("/SignUp")
    public UserDTO CreateUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO userToShow = userService.createUser(userDTO);
        return userToShow;
    }

    @PostMapping("/otpForPassword")
    public boolean checkAndSendMail(@Valid @RequestBody PasswordDTO passwordDTO) throws MessagingException {
        otpService.checkMailAndsendEMailAndSaveOtp(passwordDTO);
        return true;
    }

    @PostMapping("/VerifyEmailForPassword")
    public void verifydMailForPassword(@Valid @RequestBody PasswordDTO passwordDTO) {

        otpService.verifiedOtpAndSetPassword(passwordDTO);
    }

    @PostMapping("/SendMail")
    public boolean sendMail() throws MessagingException {
        otpService.sendEMailAndSaveOtp();
        return true;
    }

    @PostMapping("/VerifyEmail")
    public void verifydMail(@RequestBody PasswordDTO passwordDTO) {

        otpService.verifiedOtp(passwordDTO);
    }


    @PutMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
    public Account updateAccount(@PathVariable Long id, @Valid @RequestBody Account authenticationDTO)
            throws Exception {
        authenticationDTO.setId(id);
        return this.rep.save(authenticationDTO);
    }

    @GetMapping()
    public List<AccountDTO> getAccounts() {
        return this.accountService.getAccounts();
    }


	@PostMapping("/login/SendOtp")
	public void verifyInputs(@RequestBody AuthenticationDTO authenticationDTO) throws MessagingException {
		twoFactorAuth.verifyCredential(authenticationDTO);

	}

	@PostMapping("/login/VerifyOtp")
	public JwtDTO VerifyTwoStepsAuth(@RequestBody TwoFactorAuthDTO twoFactorAuthDTO) {
		return twoFactorAuth.twoStepsVerification(twoFactorAuthDTO);
	}
	
	@GetMapping(path = "/authenticated")
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	public AuthenticatedUserFullCredentialsDTO getAuthenticatedUserAccount() {
		return accountService.getAuthenticatedUserAccount();
	}
	
	@PostMapping("/save/authenticated")
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	public Long saveAuthenticatedUserData(@RequestBody AuthenticatedUserDataRequestDTO authenticatedUserDataRequestDTO ) {
		return accountService.saveAuthenticatedUserData(authenticatedUserDataRequestDTO);
	}
	
}

package effyis.partners.socle.service.implService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import effyis.partners.socle.entity.Account;
import effyis.partners.socle.entity.Role;
import effyis.partners.socle.repository.AccountRepository;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Service
public class CustomUserDetailsImplService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MessageSource messageSource;

	@Value("${default.role}")
	private String defaultRole;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Account account = this.accountRepository.findByLogin(login).orElse(null);
		List<Role> roles = new ArrayList<Role>();
		if (account == null) {
			throw new UsernameNotFoundException(
					this.messageSource.getMessage("account.not.found", null, LocaleContextHolder.getLocale()));
		}
		roles.add(account.getRole());
		List<GrantedAuthority> authorities = roles.stream()
				.map(r -> new SimpleGrantedAuthority(r == null ? this.defaultRole : r.getRole()))
				.collect(Collectors.toList());
		return new User(account.getLogin(), account.getPassword(), authorities);
	}

}

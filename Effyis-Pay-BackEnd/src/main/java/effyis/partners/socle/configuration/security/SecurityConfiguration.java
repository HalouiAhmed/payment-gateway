package effyis.partners.socle.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${security.public.endpoint}")
	private String[] public_endpoint;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Autowired
	private UserServletFilter userServletFilter;

	@Autowired
	private AuthorizationFilter authorizationFilter;

	@Autowired
	private CorsConfigurationSource corsConfigurationSource;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCrypt);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors().configurationSource(this.corsConfigurationSource);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(this.public_endpoint).permitAll();
		http.authorizeRequests().antMatchers("/effyis/api/accounts/SendMail").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/accounts/VerifyEmail").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/accounts/authenticated").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/coupons/**").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/bankAccounts/**").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/companies/**").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/shippingCosts/**").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/taxes/**").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/accounts/save/authenticated").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/products/**").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/category/**").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/cloudinary/**").authenticated();
		http.authorizeRequests().antMatchers("/effyis/api/**").hasAuthority("ADMIN").anyRequest().authenticated();
		http.addFilterBefore(this.authorizationFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(this.userServletFilter, AuthorizationFilter.class);
	}
}

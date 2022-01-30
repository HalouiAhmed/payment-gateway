package effyis.partners.socle.dto;

import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class AuthenticationDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	@NotNull
	@Size(min = 10, max = 50)
	@Schema(description = "the login of the user in db or ldap")
	private String login;
	@NotNull
	@Size(min = 8)
	@Schema(description = "the password of the user in db or ldap")
	private String password;

	@Override
	public String toString() {
		StringBuilder audit = new StringBuilder("{login : ");
		return audit.append(this.login).append(" }").toString();
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthenticationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}

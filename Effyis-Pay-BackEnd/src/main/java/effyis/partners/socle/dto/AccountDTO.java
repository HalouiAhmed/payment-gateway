package effyis.partners.socle.dto;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public class AccountDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank
	private String login;
	private RoleDTO role;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{ id: ").append(this.id).append(",\n  login: ").append(this.login)
				.append(",\n  role: ").append(this.role == null ? null : this.role.toString()).append(" }");
		return sb.toString();
	}

	public AccountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public RoleDTO getRole() {
		return this.role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

}

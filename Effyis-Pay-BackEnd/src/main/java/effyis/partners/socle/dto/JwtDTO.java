package effyis.partners.socle.dto;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class JwtDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	private String jwt;
	private String fullName;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public JwtDTO() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder audit = new StringBuilder("{jwt : ");
		return audit.append(this.jwt).append(" }").toString();
	}

	public String getJwt() {
		return this.jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}

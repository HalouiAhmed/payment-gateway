package effyis.partners.socle.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class CustomerDTO extends BaseDTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	@NotBlank(message = "entrez votre fullName")
	private String fullName; 
	@Email(message = "email non valide")
    private String email;
    private String description;
    private String postalCode;
    private String city;
    private String country;
    private String phone;
    private String adress;
    
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{ fullName: ").append(this.fullName).append(",\n  email: ").append(this.email)
				.append(",\n  taxable: ").append(this.taxable).append(",description").append(this.description).append(" }");
		return sb.toString();
	}*/
    
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}

package effyis.partners.socle.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "customer")

public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@NotBlank
	private String fullName; 
	@NotBlank
	@Email
	@Column(nullable = false,unique = true)
	private String email;
	private String postalCode;
	private String city;
	private String country;
	private String phone;
	private String adress;
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	

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

	/*@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{ fullName: ").append(this.fullName).append(",\n  email: ").append(this.email)
				.append(",\n  taxable: ").append(this.taxable).append(",description").append(this.description).append(" }");
		return sb.toString();
	}*/
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id; 
	}
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


	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Customer() {
	}

	public Customer(@NotBlank String fullName, @NotBlank @Email String email,String postalCode, String city, String country, String phone, String adress) {
		this.fullName = fullName;
		this.email = email;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.adress = adress;
	}
}

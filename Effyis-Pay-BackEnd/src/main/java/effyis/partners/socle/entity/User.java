package effyis.partners.socle.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_info")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank(message = "enter your name")
	private String fullName;
	private boolean agreeToTermes;
	@Column(nullable = true)
	private String country;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private Account account;
	@Column(nullable = true)
	private String phoneNumber;
	@Column(nullable = true)
	private String adress;
	@Column(nullable = true)
	private String postalCode;
	@Column(nullable = true)
	private String city;
	@Column(nullable = true)
	private LocalDate birthDate;

	public User() {
		super();
	}

	public User(long id, @NotBlank(message = "enter your name") String fullName, boolean agreeToTermes, String country,
			Account account, String phoneNumber, String adress, String postalCode, String city, LocalDate birthDate) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.agreeToTermes = agreeToTermes;
		this.country = country;
		this.account = account;
		this.phoneNumber = phoneNumber;
		this.adress = adress;
		this.postalCode = postalCode;
		this.city = city;
		this.birthDate = birthDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isAgreeToTermes() {
		return agreeToTermes;
	}

	public void setAgreeToTermes(boolean agreeToTermes) {
		this.agreeToTermes = agreeToTermes;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", agreeToTermes=" + agreeToTermes + ", country=" + country
				+ ", account=" + account + ", phoneNumber=" + phoneNumber + ", adress=" + adress + ", postalCode="
				+ postalCode + ", city=" + city + ", birthDate=" + birthDate + "]";
	}

}

package effyis.partners.socle.entity;




import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity

public class Otp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private  String otp;
	private LocalDateTime date ;
	@OneToOne
	@JoinColumn(name ="account_id")
	private Account account;
   
  public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Otp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Otp(String otp, LocalDateTime date,Account account) {
		super();
		this.otp = otp;
		this.date = date;
		this.account=account;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	
}

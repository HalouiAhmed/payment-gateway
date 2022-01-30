package effyis.partners.socle.entity.cloudinary;

import javax.persistence.*;

import effyis.partners.socle.entity.company.Company;


/**
 *
 * @author MAHLA Ilyasse Badreddine
 *
 */
@Entity
@Table(name = "cloudinaryInformation")
public class CloudinaryInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String public_id;
	private String format;
	private String resource_type;
	private int bytes;
	private String secure_url;

	@ManyToOne
	@JoinColumn(name = "attachement_id", nullable = false)
	private Attachement attachement;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublic_id() {
		return this.public_id;
	}

	public void setPublic_id(String public_id) {
		this.public_id = public_id;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getResource_type() {
		return this.resource_type;
	}

	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}

	public int getBytes() {
		return this.bytes;
	}

	public void setBytes(int bytes) {
		this.bytes = bytes;
	}

	public String getSecure_url() {
		return this.secure_url;
	}

	public void setSecure_url(String secure_url) {
		this.secure_url = secure_url;
	}

	public Attachement getAttachement() {
		return this.attachement;
	}

	public void setAttachement(Attachement attachement) {
		this.attachement = attachement;
	}

	public CloudinaryInformation() {
	}

	public CloudinaryInformation(String public_id, String format, String resource_type, int bytes, String secure_url, Attachement attachement) {
		this.public_id = public_id;
		this.format = format;
		this.resource_type = resource_type;
		this.bytes = bytes;
		this.secure_url = secure_url;
		this.attachement = attachement;
	}

	@Override
	public String toString() {
		StringBuilder account = new StringBuilder("{ id : ");
		return account.append(this.id).append("\n, ")
				.append("public_id : ").append(this.public_id)
				.append("format : ").append(this.format).append("\n, ")
				.append("resource_type : ").append(this.resource_type).append("\n, ")
				.append("bytes : ").append(this.bytes).append("\n, ")
				.append("secure_url : ").append(this.secure_url).append("\n, ")
				.append(" }").toString();
	}

}

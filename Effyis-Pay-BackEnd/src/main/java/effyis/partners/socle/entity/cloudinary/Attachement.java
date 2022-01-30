package effyis.partners.socle.entity.cloudinary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import effyis.partners.socle.enums.TypeAttachement;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author MAHLA Ilyasse Badreddine
 *
 */
@Entity
@NamedEntityGraph(name = "Attachement.cloudinaryInformationList", attributeNodes = @NamedAttributeNode("cloudinaryInformationList"))
@Table(name = "attachement")
public class Attachement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TypeAttachement typeAttachement;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "attachement", orphanRemoval = true)
	private List<CloudinaryInformation> cloudinaryInformationList;

	private int activeAttachement;

	public Attachement() {
	}

	public Attachement(TypeAttachement typeAttachement) {
		this.typeAttachement = typeAttachement;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeAttachement getTypeAttachement() {
		return this.typeAttachement;
	}

	public void setTypeAttachement(TypeAttachement typeAttachement) {
		this.typeAttachement = typeAttachement;
	}

	public int getActiveAttachement() {
		return this.activeAttachement;
	}

	public void setActiveAttachement(int activeAttachement) {
		this.activeAttachement = activeAttachement;
	}

	public List<CloudinaryInformation> getCloudinaryInformationList() {
		return this.cloudinaryInformationList;
	}

	public void setCloudinaryInformationList(List<CloudinaryInformation> cloudinaryInformationList) {
		this.cloudinaryInformationList = cloudinaryInformationList;
	}

	@Override
	public String toString() {
		return "Attachement [id=" + this.id + ", typeAttachement=" + this.typeAttachement + ", cloudinaryInformation="
				+ this.cloudinaryInformationList + ", activeAttachement=" + this.activeAttachement + "]";
	}

}

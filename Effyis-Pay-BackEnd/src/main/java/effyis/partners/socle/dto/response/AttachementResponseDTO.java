package effyis.partners.socle.dto.response;



import effyis.partners.socle.entity.cloudinary.CloudinaryInformation;
import effyis.partners.socle.enums.TypeAttachement;

import java.util.List;

/**
 *
 * @author MAHLA Ilyasse Badreddine
 *
 */

public class AttachementResponseDTO {

	private Long id;
	private TypeAttachement typeAttachement;
	private List<CloudinaryInformation> cloudinaryInformationList;
	private int activeAttachement;
	private int positionAttachement;
	private String storeName;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeAttachement getTypeAttachement() {
		return typeAttachement;
	}

	public void setTypeAttachement(TypeAttachement typeAttachement) {
		this.typeAttachement = typeAttachement;
	}

	public int getActiveAttachement() {
		return activeAttachement;
	}

	public void setActiveAttachement(int activeAttachement) {
		this.activeAttachement = activeAttachement;
	}

	public List<CloudinaryInformation> getCloudinaryInformationList() {
		return cloudinaryInformationList;
	}

	public void setCloudinaryInformationList(List<CloudinaryInformation> cloudinaryInformationList) {
		this.cloudinaryInformationList = cloudinaryInformationList;
	}

	public int getPositionAttachement() {
		return positionAttachement;
	}

	public void setPositionAttachement(int positionAttachement) {
		this.positionAttachement = positionAttachement;
	}

	@Override
	public String toString() {
		return "Attachement [id=" + id + ", typeAttachement=" + typeAttachement + ", cloudinaryInformation="
				+ cloudinaryInformationList + ", activeAttachement=" + activeAttachement + "]";
	}

}

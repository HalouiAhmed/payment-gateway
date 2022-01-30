package effyis.partners.socle.dto.response;


/**
 *
 * @author MAHLA Ilyasse Badreddine
 *
 */

public class CustomResponseDTO {

	private String response;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "UrlResponseDTO [response=" + response + "]";
	}

}

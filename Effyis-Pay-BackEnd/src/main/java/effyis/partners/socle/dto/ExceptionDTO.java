package effyis.partners.socle.dto;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class ExceptionDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	private String timestamp;
	private int status;
	private String error;
	private String message;
	private String path;

	@Override
	public String toString() {
		StringBuilder errorJson = new StringBuilder();
		errorJson.append("{ timestamp: ").append(this.timestamp).append(", \n status: ").append(this.status)
				.append(", \n error : ").append(this.error).append(", \n message : ").append(this.message)
				.append(", \n path : ").append(this.path).append(" }");
		return errorJson.toString();
	}

	public ExceptionDTO() {
		super();
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}

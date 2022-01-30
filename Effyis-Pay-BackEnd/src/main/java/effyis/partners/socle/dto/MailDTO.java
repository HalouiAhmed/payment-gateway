package effyis.partners.socle.dto;

import java.util.Map;

import javax.activation.DataSource;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public class MailDTO {

	/**
	 * from email is should be taking from application.yml
	 */
	private String from;
	private String to;
	private String subject;
	private String text;
	private String img;
	private DataSource datasource;
	
	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	/**
	 * template name used for email it should be in template folder under resources
	 * if template name is template.html, this field must have "template" as value
	 */
	private String template;
	/**
	 * custom properties that you want to inject in template mail like signature ...
	 */
	private Map<String, Object> props;

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFrom() {
		return this.from;
	}

	public MailDTO() {
		super();
	}

	public Map<String, Object> getProps() {
		return this.props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}

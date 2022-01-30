package effyis.partners.socle.service;

import javax.mail.MessagingException;

import effyis.partners.socle.dto.MailDTO;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public interface MailService {

	void sendMail(MailDTO mail) throws MessagingException;
	void sendMailPdf(MailDTO mail) throws MessagingException;
	

}

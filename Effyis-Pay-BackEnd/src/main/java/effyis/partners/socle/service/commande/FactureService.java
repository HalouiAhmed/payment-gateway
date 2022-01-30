package effyis.partners.socle.service.commande;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import effyis.partners.socle.dto.commande.FactureDTO;
import effyis.partners.socle.dto.commande.FactureDTOREq;
import net.sf.jasperreports.engine.JRException;

public interface FactureService {
   
	public void createFacture(FactureDTOREq factureDTO)throws FileNotFoundException, JRException, MessagingException, IOException;
	public List<FactureDTO> getListFacture();
}

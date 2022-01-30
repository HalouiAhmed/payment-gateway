package effyis.partners.socle.controller.commande;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import effyis.partners.socle.dto.commande.FactureDTOREq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import effyis.partners.socle.dto.commande.FactureDTO;
import effyis.partners.socle.service.commande.FactureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/effyis/api/command")
public class FactureController {
	@Autowired
	private FactureService factureService;
	@PostMapping()
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
	public void createCommande(@RequestBody FactureDTOREq factureDTO) throws JRException, MessagingException, IOException {
		factureService.createFacture(factureDTO);
	}
	@GetMapping()
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
  public List<FactureDTO> getListCommand() {
		return factureService.getListFacture();
	}
	

}

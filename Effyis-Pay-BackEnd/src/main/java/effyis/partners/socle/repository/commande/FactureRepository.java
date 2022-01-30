package effyis.partners.socle.repository.commande;

import effyis.partners.socle.entity.command.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture,Long> {

}

package effyis.partners.socle.controller;

import effyis.partners.socle.taxeRate.dto.response.TaxeRateResponseDTO;
import effyis.partners.socle.taxeRate.service.TaxeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/effyis/api/tax")
public class TaxController {
    @Autowired
    private TaxeRateService taxService;

    @GetMapping(value = "/getall",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TaxeRateResponseDTO> getAllTaxes(){
        return taxService.getAllTaxeRates();
    }

}

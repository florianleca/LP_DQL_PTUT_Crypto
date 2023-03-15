package fr.lpdql.ptut.cryptobotsandbox.testparameters;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestParametersRESTController {

	@GetMapping("/getdata/btc/eur")
	public String bitcoinEuro(@RequestParam String interval, @RequestParam String startdate,
			@RequestParam String enddate, Model model) {
		model.addAttribute("interval", interval);
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		// Appel à la bade de donnée avec les paramètres suivants :
		// BTC / EUR / interval / startdate / enddate
		return "Un super fichier json";
	}
}

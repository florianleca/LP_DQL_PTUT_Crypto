package fr.lpdql.ptut.cryptobotsandbox.testparameters;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestParametersController {

	@GetMapping("/")
	public String testParameters() {
		return "testparameters";
	}

	@PostMapping("/")
	public String postTestParameters(@RequestParam String symbol, @RequestParam String interval,
			@RequestParam Object startdate, @RequestParam Object enddate, Model model) {
		model.addAttribute("symbol", symbol);
		model.addAttribute("interval", interval);
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		String confirmation = "Vous avez configuré un test sur " + symbol + " avec un intervalle de " + interval
				+ " entre le " + startdate + " et le " + enddate + ".";
		model.addAttribute("confirmation", confirmation);
		return "testparameters";
	}
}

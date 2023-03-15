package fr.lpdql.ptut.cryptobotsandbox.testparameters;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestParametersController {

	@GetMapping("/")
	public String redirection() {
		return "redirect:/testparameters";
	}

	@GetMapping("/testparameters")
	public String testParameters() {
		return "testparameters";
	}
	
}

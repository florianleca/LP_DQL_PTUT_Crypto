package fr.lpdql.ptut.cryptobotsandbox.testparameters;

import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestParametersRESTController {

	@Autowired
	private TestParametersService testParametersService;
	
	@GetMapping("/getdata/btc/eur")
	public JSONObject bitcoinEuro(@RequestParam String frequency, @RequestParam String start_time,
			@RequestParam String end_time, Model model) throws SQLException {
		model.addAttribute("frequency", frequency);
		model.addAttribute("start_time", start_time);
		model.addAttribute("end_time", end_time);
		return testParametersService.establishConnection(frequency);
	}
}

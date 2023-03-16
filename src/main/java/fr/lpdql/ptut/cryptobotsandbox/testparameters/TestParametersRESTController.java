package fr.lpdql.ptut.cryptobotsandbox.testparameters;

import java.sql.SQLException;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestParametersRESTController {

	@Autowired
	private TestParametersService testParametersService;

	@GetMapping("/getdata/{crypto}/{devise}")
	public Map<String, Map<String, String>> bitcoinEuro(@PathVariable String crypto, @PathVariable String devise,
			@RequestParam String frequency, @RequestParam String startTime, @RequestParam String endTime) {
		Map<String, Map<String, String>> json = new JSONObject();
		try {
			json = testParametersService.getJsonFromDataBase(crypto, devise, frequency, startTime, endTime);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return json;
	}
}

package fr.lpdql.ptut.blocklytrader.RunTest;

import fr.lpdql.ptut.blocklytrader.datasettings.DataSettingsController;
import fr.lpdql.ptut.blocklytrader.datasettings.DataSettingsService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RunTestController {

    private final Logger logger = LoggerFactory.getLogger(DataSettingsController.class);

    @Autowired
    private RunTestService runTestService;

    @GetMapping("/runtest/")
    public Map<String, Map<String, String>> runTest(
            @RequestParam String blocklyJson,
            @RequestParam String cryptoBalance,
            @RequestParam String deviseBalance,
            @RequestParam String exchangeFees)
    {
        logger.info("Bouton Run Test --> " + cryptoBalance + " / " + deviseBalance + " / " + exchangeFees);

        //TODO virer le dummy
        Map<String, Map<String, String>> testResult = new HashMap<>();
        Map<String, String> subMap = new HashMap<>();

        subMap.put("type","buy/sell");
        subMap.put("crypto_amount","0,158");
        subMap.put("curency_amount","1500");
        subMap.put("rate","15678");

        testResult.put("16765320",subMap);
        /*try {
            testResult = runTestService.getTestResult(cryptoBalance, deviseBalance, exchangeFees);
        } catch (SQLException e) {
            logger.warn("Exception à traiter : " + e);
        }*/
        System.out.println("runTest dans rutestcontroller");
        //----------------------
        return testResult;
    }
}

package fr.lpdql.ptut.blocklytrader.datasettings;

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
public class DataSettingsController {

    private final Logger logger = LoggerFactory.getLogger(DataSettingsController.class);


    @Autowired
    private DataSettingsService dataSettingsService;

    @GetMapping("/getdata/{crypto}/{devise}")
    public Map<String, Map<String, String>> submitData(@PathVariable String crypto, @PathVariable String devise, @RequestParam String frequency, @RequestParam String startTime, @RequestParam String endTime) {
        logger.info("Bouton Submit (data) --> " + crypto + " / " + devise + " / " + frequency);
        Map<String, Map<String, String>> json = new HashMap<>();
        try {
            json = dataSettingsService.getJsonFromDataBase(crypto, devise, frequency, startTime, endTime);
        } catch (SQLException e) {
            logger.warn("Exception à traiter : " + e);
        }
        return json;
    }
}

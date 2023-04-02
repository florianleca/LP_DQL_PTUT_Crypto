package fr.lpdql.ptut.blocklytrader.runtest;

import fr.lpdql.ptut.blocklytrader.datasettings.DataSettingsController;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
public class RunTestController {

    private final Logger logger = LoggerFactory.getLogger(DataSettingsController.class);
    private final RunTestService runTestService;

    @Autowired
    public RunTestController(RunTestService runTestService) {
        this.runTestService = runTestService;
    }

    @GetMapping("/runtest/")
    public Map<Object, Object> runTest(@RequestParam String blocklyJson, @RequestParam String cryptoBalance,
                                       @RequestParam String deviseBalance, @RequestParam String exchangeFees) throws ParseException {
        logger.info("Bouton Run Test --> " + cryptoBalance + " / " + deviseBalance + " / " + exchangeFees);
        return runTestService.getTestResult(blocklyJson, cryptoBalance, deviseBalance, exchangeFees);
    }

}

package fr.lpdql.ptut.blocklytrader.runtest;

import com.jayway.jsonpath.PathNotFoundException;
import fr.lpdql.ptut.blocklytrader.datasettings.DataSettingsController;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleParseException() {
        String message = "Une exception de type ParseException s'est produite.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(PathNotFoundException.class)
    public ResponseEntity<String> handlePathNotFoundException() {
        String message = "Une exception de type PathNotFound s'est produite.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException() {
        String message = "Une exception de type IllegalArgument s'est produite.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}

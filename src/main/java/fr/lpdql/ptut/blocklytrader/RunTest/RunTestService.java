package fr.lpdql.ptut.blocklytrader.RunTest;

import fr.lpdql.ptut.blocklytrader.datasettings.DataSettingsService;
import fr.lpdql.ptut.blocklytrader.deserialisation.BlocklyJsonParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class RunTestService {

    public static SortedMap<String, Map<String, String>> transactions = new TreeMap<>();
    public static Map.Entry currentEntry;
    public static double currentCryptoBalance;
    public static double currentDeviseBalance;
    @Autowired
    private DataSettingsService dataSettingsService;


    public static void addTransaction(String type, double cryptoAmount, double currencyAmount, double rate) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("crypto_amount", String.valueOf(cryptoAmount));
        map.put("currency_amount", String.valueOf(currencyAmount));
        map.put("rate", String.valueOf(rate));
        String timestamp = (String) currentEntry.getKey();
        transactions.put(timestamp, map);
    }

    public Map<String, Map<String, String>> getTestResult(String blocklyJson,
                                                          String cryptoBalance,
                                                          String deviseBalance,
                                                          String exchangeFees) throws ParseException {
        transactions = new TreeMap<>();
        currentCryptoBalance = Double.parseDouble(cryptoBalance);
        currentDeviseBalance = Double.parseDouble(deviseBalance);
        SortedMap<String, Map<String, String>> klinesJson = dataSettingsService.getCurrentUserDataSet();

        // boucler sur klinesJson
        for (Map.Entry entry : klinesJson.entrySet()) {
            // on parse blockly avec ce jeu de valeur
            currentEntry = entry;
            BlocklyJsonParser blocklyJsonParser = new BlocklyJsonParser(blocklyJson);
            blocklyJsonParser.processEachBlock();
        }
        return transactions;
    }
}

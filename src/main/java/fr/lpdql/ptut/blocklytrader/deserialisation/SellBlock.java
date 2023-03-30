package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;
import fr.lpdql.ptut.blocklytrader.runtest.RunTestService;

import java.util.Map;

public class SellBlock extends Block {

    private final String unit;

    private final double amount;

    protected SellBlock(Map<String, String> block) {
        super(block);
        unit = JsonPath.read(blockJson, "$.fields.UNIT");
        Map<String, String> amountBlock = JsonPath.read(blockJson, "$.inputs.VALUE.block");
        amount = parseDoubleBlock(amountBlock);
        makeTransaction();
    }

    private void makeTransaction() {
        if (RunTestService.currentCryptoBalance != 0) {
            double cryptoDepense = 0.;
            if (unit.equals("%")) {
                cryptoDepense = (amount / 100.) * RunTestService.currentCryptoBalance;
            } else if (unit.equals("$")) {
                cryptoDepense = Math.min(amount, RunTestService.currentCryptoBalance);
            } else {
                System.out.println("Houston we've got a problem");
            }
            RunTestService.currentCryptoBalance -= cryptoDepense;
            Map<String, String> map = (Map<String, String>) RunTestService.currentEntry.getValue();
            double cryptoRate = Double.parseDouble(map.get("close"));
            RunTestService.currentDeviseBalance += cryptoDepense * cryptoRate;
            RunTestService.addTransaction("sell", cryptoDepense, cryptoDepense * cryptoRate, cryptoRate);

        }
    }
}

package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;
import fr.lpdql.ptut.blocklytrader.runtest.RunTestService;

import java.util.Map;

public class BuyBlock extends Block {

    private final String unit;
    private final double amount;

    protected BuyBlock(Map<String, String> block) {
        super(block);
        unit = JsonPath.read(blockJson, "$.fields.UNIT");
        Map<String, String> amountBlock = JsonPath.read(blockJson, "$.inputs.VALUE.block");
        amount = parseDoubleBlock(amountBlock);
        makeTransaction();
    }

    private void makeTransaction() {
        if (RunTestService.currentDeviseBalance != 0) {
            double spentAmount = 0.;
            if (unit.equals("%")) {
                spentAmount = (amount / 100.) * RunTestService.currentDeviseBalance;
            } else if (unit.equals("$")) {
                spentAmount = Math.min(amount, RunTestService.currentDeviseBalance);
            } else {
                System.out.println("Houston we've got a problem");
            }
            RunTestService.currentDeviseBalance -= spentAmount;
            Map<String, String> map = RunTestService.currentEntry.getValue();
            double cryptoRate = Double.parseDouble(map.get("close"));
            RunTestService.currentCryptoBalance += spentAmount / cryptoRate;
            RunTestService.addTransaction("buy", spentAmount / cryptoRate, spentAmount, cryptoRate);
        }
    }

}

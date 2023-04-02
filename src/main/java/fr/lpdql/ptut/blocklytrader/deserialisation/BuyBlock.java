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
            switch (unit) {
                case "%" -> spentAmount = (amount / 100.) * RunTestService.currentDeviseBalance;
                case "$" -> spentAmount = Math.min(amount, RunTestService.currentDeviseBalance);
                default -> logger.warn("Transaction de bloc 'BuyBlock' non reconnue : " + unit);
            }
            RunTestService.currentDeviseBalance -= spentAmount;
            double cryptoRate = Double.parseDouble(RunTestService.currentEntry.getValue().get("close"));
            RunTestService.currentCryptoBalance += spentAmount / cryptoRate;
            RunTestService.addTransaction("buy", spentAmount / cryptoRate, spentAmount, cryptoRate);
        }
    }

}

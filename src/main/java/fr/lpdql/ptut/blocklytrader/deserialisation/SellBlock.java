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
            double spentCrypto = 0.;
            switch (unit) {
                case "%" -> spentCrypto = (amount / 100.) * RunTestService.currentCryptoBalance;
                case "$" -> spentCrypto = Math.min(amount, RunTestService.currentCryptoBalance);
                default -> logger.warn("Transaction de bloc 'SellBlock' non reconnue : " + unit);
            }
            RunTestService.currentCryptoBalance -= spentCrypto;
            double cryptoRate = Double.parseDouble(RunTestService.currentEntry.getValue().get("close"));
            RunTestService.currentDeviseBalance += spentCrypto * cryptoRate;
            RunTestService.addTransaction("sell", spentCrypto, spentCrypto * cryptoRate, cryptoRate);
        }
    }

}

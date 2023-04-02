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
            if (unit.equals("%")) {
                spentCrypto = (amount / 100.) * RunTestService.currentCryptoBalance;
            } else if (unit.equals("$")) {
                spentCrypto = Math.min(amount, RunTestService.currentCryptoBalance);
            } else {
                System.out.println("Houston we've got a problem");
            }
            RunTestService.currentCryptoBalance -= spentCrypto;
            Map<String, String> map = RunTestService.currentEntry.getValue();
            double cryptoRate = Double.parseDouble(map.get("close"));
            RunTestService.currentDeviseBalance += spentCrypto * cryptoRate;
            RunTestService.addTransaction("sell", spentCrypto, spentCrypto * cryptoRate, cryptoRate);
        }
    }

}

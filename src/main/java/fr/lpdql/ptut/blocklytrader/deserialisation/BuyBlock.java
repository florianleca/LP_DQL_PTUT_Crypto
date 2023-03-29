package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;

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
        if (testRunner.currencyBalance !=0) {
            double montantDepense = 0.;
            if (unit.equals("%")) {
                montantDepense = (amount/100.)*testRunner.currencyBalance;
            } else if (unit.equals("$")) {
                montantDepense = Math.min(amount, testRunner.currencyBalance);
            } else {
                System.out.println("Houston we've got a problem");
            }
            System.out.println("On dépense " + montantDepense);
            double ancienSoldeDevise = testRunner.currencyBalance;
            testRunner.currencyBalance -= montantDepense;
            System.out.println("Le solde ($) passe de " + ancienSoldeDevise + " à " + testRunner.currencyBalance);
            double ancienSoldeCoin = testRunner.cryptoBalance;
            double cryptoRate = testRunner.oneLineKlinesVariable.get("close");
            testRunner.cryptoBalance += montantDepense/cryptoRate;
            System.out.println("Le solde (BTC) passe de " + ancienSoldeCoin + " à " + testRunner.cryptoBalance);
        }
    }
}

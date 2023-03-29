package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;

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
        if (testRunner.cryptoBalance !=0) {
            double cryptoDepense = 0.;
            if (unit.equals("%")) {
                cryptoDepense = (amount/100.)*testRunner.cryptoBalance;
            } else if (unit.equals("$")) {
                cryptoDepense = Math.min(amount, testRunner.cryptoBalance);
            } else {
                System.out.println("Houston we've got a problem");
            }
            System.out.println("On vend " + cryptoDepense + " crypto");
            double ancienSoldeCrypto = testRunner.cryptoBalance;
            testRunner.cryptoBalance -= cryptoDepense;
            System.out.println("Le solde (crypto) passe de " + ancienSoldeCrypto + " à " + testRunner.cryptoBalance);
            double ancienSoldeDevise = testRunner.currencyBalance;
            double cryptoRate = testRunner.oneLineKlinesVariable.get("close");
            testRunner.currencyBalance += cryptoDepense*cryptoRate;
            System.out.println("Le solde ($) passe de " + ancienSoldeDevise + " à " + testRunner.currencyBalance);
        }
    }
}

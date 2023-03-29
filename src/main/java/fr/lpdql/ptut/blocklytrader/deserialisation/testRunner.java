package fr.lpdql.ptut.blocklytrader.deserialisation;

import java.util.HashMap;
import java.util.Map;

public class testRunner {

    public static Map<String, Double> oneLineKlinesVariable = new HashMap<>();
    public static double cryptoBalance = 10;
    public static double currencyBalance = 10000;

    static {
        oneLineKlinesVariable.put("low", 12.3);
        oneLineKlinesVariable.put("high", 23.4);
        oneLineKlinesVariable.put("open", 34.5);
        oneLineKlinesVariable.put("close", 45600.);
        oneLineKlinesVariable.put("volume", 56700.);
        oneLineKlinesVariable.put("trades", 67.8);
    }


}

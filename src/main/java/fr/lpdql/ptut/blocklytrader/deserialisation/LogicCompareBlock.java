package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;

import java.util.Map;

public class LogicCompareBlock extends Block {

    private final String operation;
    private final double memberA;
    private final double memberB;
    private final boolean result;

    public LogicCompareBlock(Map<String, String> block) {
        super(block);
        operation = JsonPath.read(blockJson, "$.fields.OP");
        memberA = parseDoubleMember("A");
        memberB = parseDoubleMember("B");
        result = processBlock();
    }

    // un block logic_compare doit renvoyer un boolean
    public boolean processBlock() {
        boolean result = false;
        switch (operation) {
            case "EQ" -> result = memberA == memberB;
            case "NEQ" -> result = memberA != memberB;
            case "LT" -> result = memberA < memberB;
            case "LTE" -> result = memberA <= memberB;
            case "GT" -> result = memberA > memberB;
            case "GTE" -> result = memberA >= memberB;
            default -> logger.warn("Operation de bloc 'LogicCompareBlock' non reconnue : " + operation);
        }
        return result;
    }

    public boolean getResult() {
        return result;
    }

}

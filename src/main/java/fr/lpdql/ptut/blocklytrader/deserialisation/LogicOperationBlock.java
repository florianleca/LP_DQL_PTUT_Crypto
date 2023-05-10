package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;

import java.util.Map;

public class LogicOperationBlock extends Block {

    private final String operation;
    private final boolean memberA;
    private final boolean memberB;
    private final boolean result;

    public LogicOperationBlock(Map<String, String> block) {
        super(block);
        operation = JsonPath.read(blockJson, "$.fields.OP");
        memberA = parseBooleanMember("A");
        memberB = parseBooleanMember("B");
        result = processBlock();
    }

    // un block logic_compare doit renvoyer un boolean
    public boolean processBlock() {
        boolean result = false;
        switch (operation) {
            case "AND" -> result = memberA && memberB;
            case "OR" -> result = memberA || memberB;
            default -> logger.warn("Operation de bloc 'LogicOperationBlock' non reconnue : " + operation);
        }
        return result;
    }

    public boolean getResult() {
        return result;
    }

}

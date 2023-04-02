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
        switch (operation) {
            case "AND" -> {
                System.out.println("\nOpération AND");
                return memberA && memberB;
            }
            case "OR" -> {
                System.out.println("\nOpération OR");
                return memberA || memberB;
            }
            default -> System.out.println("\nOpération non reconnue ");
        }
        return false;
    }

    public boolean getResult() {
        return result;
    }

}

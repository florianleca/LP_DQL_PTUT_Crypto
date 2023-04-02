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
        switch (operation) {
            case "EQ" -> {
                System.out.println("Opération =");
                return memberA == memberB;
            }
            case "NEQ" -> {
                System.out.println("Opération !=");
                return memberA != memberB;
            }
            case "LT" -> {
                System.out.println("Opération <");
                return memberA < memberB;
            }
            case "LTE" -> {
                System.out.println("Opération <=");
                return memberA <= memberB;
            }
            case "GT" -> {
                System.out.println("Opération >");
                return memberA > memberB;
            }
            case "GTE" -> {
                System.out.println("Opération >=");
                return memberA >= memberB;
            }
            default -> System.out.println("Opération non reconnue ");
        }
        return false;
    }

    public boolean getResult() {
        return result;
    }

}

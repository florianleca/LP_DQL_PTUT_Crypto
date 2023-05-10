package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;

import java.util.Map;

public class MathArithmeticBlock extends Block {

    private final String operation;
    private final double memberA;
    private final double memberB;
    private final double result;

    public MathArithmeticBlock(Map<String, String> block) {
        super(block);
        operation = JsonPath.read(blockJson, "$.fields.OP");
        memberA = parseDoubleMember("A");
        memberB = parseDoubleMember("B");
        result = processBlock();
    }

    // un block math_arithmetic doit renvoyer un double
    public double processBlock() {
        double result = 0;
        switch (operation) {
            case "ADD" -> result = memberA + memberB;
            case "MINUS" -> result = memberA - memberB;
            case "MULTIPLY" -> result = memberA * memberB;
            case "DIVIDE" -> result = memberA / memberB;
            case "POWER" -> result = Math.pow(memberA, memberB);
            default -> logger.warn("Operation de bloc 'MathArithmeticBlock' non reconnue : " + operation);
        }
        return result;
    }

    public double getResult() {
        return result;
    }

}

package fr.lpdql.ptut.blocklytrader.deserialisation;

import java.util.Map;

import com.jayway.jsonpath.JsonPath;

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
        switch (operation) {
            case "ADD" -> {
                System.out.println("Opération ADD");
                return memberA + memberB;
            }
            case "MINUS" -> {
                System.out.println("Opération MINUS");
                return memberA - memberB;
            }
            case "MULTIPLY" -> {
                System.out.println("Opération MULTIPLY");
                return memberA * memberB;
            }
            case "DIVIDE" -> {
                System.out.println("Opération DIVIDE");
                return memberA / memberB;
            }
            case "POWER" -> {
                System.out.println("Opération POWER");
                return Math.pow(memberA, memberB);
            }
            default -> System.out.println("Opération non reconnue ");
        }
        return -1;
    }

    public double getResult() {
        return result;
    }

}

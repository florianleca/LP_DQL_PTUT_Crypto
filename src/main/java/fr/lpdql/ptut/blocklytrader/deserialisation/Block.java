package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;
import fr.lpdql.ptut.blocklytrader.RunTest.RunTestService;
import net.minidev.json.JSONObject;

import java.util.Map;

public abstract class Block {

    protected Map<String, String> blockMap;
    protected JSONObject blockJson;

    protected Block(Map<String, String> block) {
        blockMap = block;
        blockJson = new JSONObject(blockMap);
    }

    protected double valueFromMathNumber(Map<String, String> block) {
        JSONObject blockBis = new JSONObject(block);
        String valueString = (JsonPath.read(blockBis, "$.fields.NUM")).toString();
        double value = Double.parseDouble(valueString);
        System.out.println("Ce membre est un nombre, sa valeur est " + value);
        return value;
    }

    private double valueFromKlinesVariable(Map<String, String> block) {
        JSONObject blockBis = new JSONObject(block);
        String variable = JsonPath.read(blockBis, "$.fields.DATA_TYPE");
        Map<String, String> map = (Map<String, String>) RunTestService.currentEntry.getValue();
        double value = Double.parseDouble(map.get(variable));
        System.out.println("Variable : " + variable + " ; sa valeur est " + value);
        return value;
    }


    protected boolean parseBooleanMember(String memberLetter) {
        Map<String, String> memberBlock = JsonPath.read(blockJson, "$.inputs." + memberLetter + ".block");
        return parseBooleanBlock(memberBlock);
    }

    protected boolean parseBooleanBlock(Map<String, String> block) {
        boolean result = false;
        String type = block.get("type");
        switch (type) {
            case "logic_compare" -> result = new LogicCompareBlock(block).getResult();
            case "logic_operation" -> result = new LogicOperationBlock(block).getResult();
            default -> System.out.println("Block de type 'booléen' inconnu");
        }
        System.out.println("Le résultat est : " + result);
        return result;
    }

    protected double parseDoubleMember(String memberLetter) {
        Map<String, String> memberBlock = JsonPath.read(blockJson, "$.inputs." + memberLetter + ".block");
        return parseDoubleBlock(memberBlock);
    }

    protected double parseDoubleBlock(Map<String, String> block) {
        double value = 0.0;
        String type = block.get("type");
        switch (type) {
            case "math_number" -> value = valueFromMathNumber(block);
            case "math_arithmetic" -> value = new MathArithmeticBlock(block).getResult();
            case "klines_variables" -> value = valueFromKlinesVariable(block);
            default -> System.out.println("Bloc valeur non reconnu");
        }
        return value;
    }
}

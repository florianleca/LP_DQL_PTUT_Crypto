package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;
import fr.lpdql.ptut.blocklytrader.datasettings.DataSettingsController;
import fr.lpdql.ptut.blocklytrader.runtest.RunTestService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class Block {

    protected final Logger logger = LoggerFactory.getLogger(DataSettingsController.class);
    protected Map<String, String> blockMap;
    protected JSONObject blockJson;

    protected Block(Map<String, String> block) {
        blockMap = block;
        blockJson = new JSONObject(blockMap);
    }

    protected double valueFromMathNumber(Map<String, String> block) {
        JSONObject blockBis = new JSONObject(block);
        String valueString = (JsonPath.read(blockBis, "$.fields.NUM")).toString();
        return Double.parseDouble(valueString);
    }

    private double valueFromKlinesVariable(Map<String, String> block) {
        JSONObject blockBis = new JSONObject(block);
        String variable = JsonPath.read(blockBis, "$.fields.DATA_TYPE");
        Map<String, String> map = RunTestService.currentEntry.getValue();
        return Double.parseDouble(map.get(variable));
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
            default -> logger.warn("Type de bloc 'booléen' non reconnu : " + type);
        }
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
            default -> logger.warn("Type de bloc 'valeur' non reconnu : " + type);
        }
        return value;
    }

}

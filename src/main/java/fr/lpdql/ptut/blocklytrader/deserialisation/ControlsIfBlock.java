package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;

import java.util.Map;

public class ControlsIfBlock extends Block {

    private final Map<String, String> conditionBlock;
    private final Map<String, String> bodyBlock;

    public ControlsIfBlock(Map<String, String> block) {
        super(block);
        conditionBlock = JsonPath.read(blockJson, "$.inputs.IF0.block");
        bodyBlock = JsonPath.read(blockJson, "$.inputs.DO0.block");
        processBlock();
    }

    // un block controls_if ne renvoie rien
    public void processBlock() {
        if (processCondition(conditionBlock)) {
            processBody(bodyBlock);
        }
        // TODO: Traiter le cas où il y a un block NEXT
    }

    private boolean processCondition(Map<String, String> condition) {
        return parseBooleanBlock(condition);
    }

    private void processBody(Map<String, String> body) {
        String type = body.get("type");
        switch (type) {
            case "buy" -> new BuyBlock(body);
            case "sell" -> new SellBlock(body);
            default -> logger.warn("Type de bloc 'action' non reconnu : " + type);
        }
    }

}

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
            System.out.println("La condition du if est TRUE");
            processBody(bodyBlock);
        } else {
            System.out.println("La condition du if est FALSE");
        }
        // Traiter le cas où il y a un block NEXT
    }

    private boolean processCondition(Map<String, String> condition) {
        System.out.println("On vérifie la condition du if...");
        return parseBooleanBlock(condition);
    }

    private void processBody(Map<String, String> body) {
        System.out.println("On effectue les instructions du body du if...");
        String type = body.get("type");
        switch (type) {
            case "buy" -> {
                System.out.println("Let's BUY some crypto");
                new BuyBlock(body);
            }
            case "sell" -> {
                System.out.println("Let's SELL some crypto");
                new SellBlock(body);
            }
            default -> System.out.println("Bloc de type ACTION non reconnu");
        }
    }

}

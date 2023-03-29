package fr.lpdql.ptut.blocklytrader.deserialisation;

import com.jayway.jsonpath.JsonPath;
import fr.lpdql.ptut.blocklytrader.datasettings.DataSettingsController;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * logic_compare (EQ, NEQ, LT, LTE, GT, GTE)
 * math_number (juste un nombre, ex : 123)
 * controls_if
 * klines_variables (open, close, low, high, volume)
 * buy (%, $)
 * sell (%, coin)
 */

public class BlocklyJsonParser {

    private final Logger logger = LoggerFactory.getLogger(DataSettingsController.class);
    private final File jsonFile;
    private final List<Map<String, String>> blocks;


    // Constructeur prenant le json (String) en entrée
    public BlocklyJsonParser(String jsonFilePath) throws IOException {
        jsonFile = new File(jsonFilePath);
        blocks = parseBlocks();
    }


    // une methode en extrait les blocks
    public List<Map<String, String>> parseBlocks() throws IOException {
        List<Map<String, String>> blocksList = new ArrayList<>();
        JSONArray blocksJsonArray = JsonPath.read(jsonFile, "$.blocks.blocks");
        for (Object obj : blocksJsonArray) {
            Map<String, String> map = (LinkedHashMap<String, String>) obj;
            blocksList.add(map);
        }
        return blocksList;
    }

    // une méthode itère sur la liste de blocks pour les traiter selon leur type
    public void processEachBlock() {
        for (Map<String, String> block : blocks) {
            processBlock(block);
        }
    }

    // pour l'instant, on ne peut commencer un bot que par un controls_if
    public void processBlock(Map<String, String> block) {
        String type = block.get("type");
        switch (type) {
            case "controls_if" -> {
                logger.info("Bot commençant par un bloc 'controls_if : lançons son traitement");
                new ControlsIfBlock(block);
            }
            default -> logger.warn("Ce bot ne commence pas par un bloc 'controls_if'.");
        }
    }
}

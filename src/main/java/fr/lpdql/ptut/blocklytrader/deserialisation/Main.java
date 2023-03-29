package fr.lpdql.ptut.blocklytrader.deserialisation;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {



        BlocklyJsonParser blocklyJsonParser = new BlocklyJsonParser("src/main/resources/simpleBotSprint3.json");
        blocklyJsonParser.processEachBlock();
    }
}

package fr.lpdql.ptut.blocklytrader.klines;

import org.springframework.stereotype.Component;

@Component
public class CollectionSelector {

    private String currentCollection = "";

    public String getCurrentCollection() {
        return currentCollection;
    }

    public void setCurrentCollection(String collection) {
        currentCollection = collection;
    }

}

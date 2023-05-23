package fr.lpdql.ptut.blocklytrader.klines;

import org.springframework.stereotype.Component;

@Component
public class CollectionSelector {

    private boolean isBusy = false;
    private String currentCollection = "";

    // utilisé par Spring, ne pas supprimer
    public String getCurrentCollection() {
        return currentCollection;
    }

    public void setCurrentCollection(String collection) {
        currentCollection = collection;
    }

    public void setIsBusy(boolean state) {
        isBusy = state;
    }

    public boolean getIsBusy() {
        return isBusy;
    }

}

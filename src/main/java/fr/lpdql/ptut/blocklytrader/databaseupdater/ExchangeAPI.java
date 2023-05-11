package fr.lpdql.ptut.blocklytrader.databaseupdater;

import org.json.JSONArray;

public interface ExchangeAPI {
    JSONArray getJSONKlinesFromStartTime(long startTime);
}

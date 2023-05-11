package fr.lpdql.ptut.blocklytrader.databaseupdater;


import org.json.JSONArray;

public class DataBaseUpdater {

    private final DataBaseConnector dataBaseConnector;
    private final ExchangeAPI exchangeAPI;

    public DataBaseUpdater(
            String symbol,
            String interval,
            ExchangeAPI exchangeAPI,
            DataBaseConnector dataBaseConnector
    ) {
        this.symbol = symbol;
        this.interval = interval;
        this.exchangeAPI = exchangeAPI;
        this.dataBaseConnector = dataBaseConnector;
    }

    long getEndTime(){
        long now = System.currentTimeMillis();
        return now - (10 * 60 * 60 * 1000); // 10 hours ago
    }
    public void updateKlines() {
        long endTime = getEndTime();
        long lastTimestamp = dataBaseConnector.getLastTimestamp();
        long startTime = lastTimestamp + 1;
        // Boucle pour récupérer les nouvelles klines jusqu'à endTime
        while (startTime < endTime) {
            JSONArray klinesArray = exchangeAPI.getJSONKlinesFromStartTime(startTime);
            // Insertion des nouvelles klines dans la base de données
            for (int i = 0; i < klinesArray.length(); i++) {
                Kline kline = new Kline(klinesArray.getJSONArray(i));
                dataBaseConnector.addKlineToDB(kline);
            }
            lastTimestamp = dataBaseConnector.getLastTimestamp();
            startTime = lastTimestamp + 1;
        }
    }

}


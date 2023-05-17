package fr.lpdql.ptut.blocklytrader.databaseupdater;


import org.json.JSONArray;

public class DataBaseUpdater {

    private final DataBaseConnector dataBaseConnector;
    private final ExchangeAPI exchangeAPI;
    private final Class<Kline> usedKlineClasse;

    public DataBaseUpdater(
            ExchangeAPI exchangeAPI,
            DataBaseConnector dataBaseConnector,
            Class<Kline> usedKlineClass //nécessaire aux tests unitaires
    ) {
        this.exchangeAPI = exchangeAPI;
        this.dataBaseConnector = dataBaseConnector;
        this.usedKlineClasse = usedKlineClass;
    }
    public DataBaseUpdater(
            ExchangeAPI exchangeAPI,
            DataBaseConnector dataBaseConnector
    ) {
        this.exchangeAPI = exchangeAPI;
        this.dataBaseConnector = dataBaseConnector;
        this.usedKlineClasse = Kline.class;
    }

    long getEndTime(){
        long now = System.currentTimeMillis();
        return now - (10 * 60 * 60 * 1000); // 10 hours ago
    }
    public void updateKlines() throws DataBaseUpdaterException {
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

    public String getTableName() {
        return dataBaseConnector.getTableName();
    }
}


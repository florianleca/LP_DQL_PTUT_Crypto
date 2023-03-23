package fr.lpdql.ptut.cryptobotsandbox.databaseupdater;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class DataBaseUpdater {
	private final String symbol;
	private final String interval;
	private final DataBaseInterface dataBaseInterface;
    private final OkHttpClient httpClient;

    public DataBaseUpdater(String symbol, String interval, DataBaseInterface dataBaseInterface) {
    	this.symbol = symbol;
    	this.interval  = interval;
    	this.dataBaseInterface = dataBaseInterface;

        // Initialisation du client HTTP
        httpClient = new OkHttpClient();
    }
    
    public void updateKlines() throws SQLException, JSONException, IOException {

        // Boucle pour récupérer les nouvelles klines jusqu'à moins de 10 heures avant l'heure actuelle
        long now = System.currentTimeMillis();
        long endTime = now - (10 * 60 * 60 * 1000); // 10 hours ago
        long lastTimestamp;
        long startTime = 0;

        while (startTime < endTime) {
            lastTimestamp = dataBaseInterface.getLastTimestamp();
            startTime = lastTimestamp + 1;
            
            // Récupération des nouvelles klines à partir de l'API Binance
			Request request = new Request.Builder()
                .url(String.format("https://api.binance.com/api/v3/klines?symbol=%s&interval=%s&startTime=%d", symbol, interval, startTime))
                .build();
            Response response = httpClient.newCall(request).execute();
            JSONArray klinesArray = new JSONArray(response.body().string());

            // Insertion des nouvelles klines dans la base de données
            for (int i = 0; i < klinesArray.length(); i++) {
                Kline kline = new Kline(klinesArray.getJSONArray(i));
                dataBaseInterface.addKlineToDB(kline);
            }
        }
    }
}


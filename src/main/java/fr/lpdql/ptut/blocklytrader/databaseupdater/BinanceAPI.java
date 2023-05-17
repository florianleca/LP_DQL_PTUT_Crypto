package fr.lpdql.ptut.blocklytrader.databaseupdater;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.slf4j.Logger;

import java.io.IOException;

public class BinanceAPI implements ExchangeAPI {
    private String apiUrl = "https://api.binance.com/api/v3/klines?symbol=%s&interval=%s&startTime=%d";
    private final OkHttpClient httpClient;
    private final Logger logger;

    public BinanceAPI(Logger logger, String symbol, String interval){
        this.logger = logger;
        apiUrl = String.format(apiUrl, symbol, interval);
        // Initialisation du client HTTP
        httpClient = new OkHttpClient();

    }
    public JSONArray getJSONKlinesFromStartTime(long startTime) throws DataBaseUpdaterException {
        try {
            // Récupération des nouvelles klines à partir de l'API Binance
            Request request = new Request.Builder().url(String.format(apiUrl, startTime)).build();
            Response response = httpClient.newCall(request).execute();
            return new JSONArray(response.body().string());
        }catch (IOException exc){
            logger.warn("Exception à traiter : " + exc);
            throw new DataBaseUpdaterException(exc);
        }
    }
}

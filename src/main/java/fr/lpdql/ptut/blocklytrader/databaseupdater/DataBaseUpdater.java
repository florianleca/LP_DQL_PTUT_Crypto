package fr.lpdql.ptut.blocklytrader.databaseupdater;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import fr.lpdql.ptut.blocklytrader.klines.CollectionSelector;
import fr.lpdql.ptut.blocklytrader.klines.KlineDocument;
import fr.lpdql.ptut.blocklytrader.klines.KlineRepository;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class DataBaseUpdater {

    public static final String BINANCE_API_URL = "https://api.binance.com/api/v3/klines?symbol=%s&interval=%s" +
            "&startTime=%d";
    private final String symbol;
    private final String interval;
    private final OkHttpClient httpClient;
    private final String collectionName;
    private final KlineRepository klineRepository;
    private final CollectionSelector collectionSelector;

    public DataBaseUpdater(String symbol, String interval, String collectionName, KlineRepository klineRepository,
                           CollectionSelector collectionSelector) {
        this.klineRepository = klineRepository;
        this.collectionSelector = collectionSelector;
        this.symbol = symbol;
        this.interval = interval;
        this.collectionName = collectionName;
        httpClient = new OkHttpClient();
    }

    public void updateKlines() throws JSONException, IOException {
        // Boucle pour récupérer les nouvelles klines jusqu'à moins de 10 heures avant l'heure actuelle
        long now = System.currentTimeMillis();
        long endTime = now - (10 * 60 * 60 * 1000); // 10 hours ago
        long lastTimestamp;
        long startTime = 0;
        while (startTime < endTime) {
            lastTimestamp = getLastTimestamp(collectionName);
            startTime = lastTimestamp + 1;
            JSONArray klinesArray = getKlinesFromBinance(startTime);
            addKlinesToTB(klinesArray);
        }
    }

    private JSONArray getKlinesFromBinance(long startTime) throws IOException {
        Request request = new Request.Builder().url(String.format(BINANCE_API_URL, symbol, interval, startTime))
                .build();
        Response response = httpClient.newCall(request).execute();
        return new JSONArray(response.body().string());
    }

    public long getLastTimestamp(String collectionName) {
        collectionSelector.setCurrentCollection(collectionName);
        KlineDocument kline = klineRepository.findFirstByOrderByOpenTimeDesc();
        return Long.parseLong(kline.getOpenTime());
    }

    public void addKlinesToTB(JSONArray klinesArray) {
        for (int i = 0; i < klinesArray.length(); i++) {
            KlineDocument kline = new KlineDocument(klinesArray.getJSONArray(i));
            klineRepository.insert(kline);
        }
    }

}


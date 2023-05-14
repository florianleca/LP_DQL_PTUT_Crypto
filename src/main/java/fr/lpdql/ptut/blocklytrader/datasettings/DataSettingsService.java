package fr.lpdql.ptut.blocklytrader.datasettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataSettingsService {

    @Autowired
    private KlineRepository klineRepository;
    private SortedMap<String, Map<String, String>> currentUserDataSet;

    public String currentCollection;

    public Map<String, Map<String, String>> getJsonFromDataBase(String crypto, String devise, String frequency,
                                                                String startTime, String endTime) {
        String nomDeLaTable = crypto.toUpperCase() + "_" + devise.toUpperCase() + "_" + frequency;
        SortedMap<String, Map<String, String>> json = new TreeMap<>();
        List<KlineDocument> result = executeDataBaseQuery(nomDeLaTable, startTime, endTime);
        result.forEach(kline -> json.put(kline.getOpenTime(), extractDataFromSingleLine(kline)));
        currentUserDataSet = json;
        return json;
    }

    public List<KlineDocument> executeDataBaseQuery(String nomDeLaCollection, String startTime, String endTime) {
        currentCollection = nomDeLaCollection;
        return klineRepository.findKlinesBetweenDates(Float.parseFloat(startTime), Float.parseFloat(endTime));
    }

    public Map<String, String> extractDataFromSingleLine(KlineDocument document) {
        Map<String, String> subJson = new HashMap<>();
        subJson.put("open_time", document.getOpenTime());
        subJson.put("open", document.getOpen());
        subJson.put("high", document.getHigh());
        subJson.put("low", document.getLow());
        subJson.put("close", document.getClose());
        subJson.put("volume", document.getVolume());
        subJson.put("close_time", document.getCloseTime());
        subJson.put("quote_asset_volume", document.getQuoteAssetVolume());
        subJson.put("number_of_trades", document.getNumberOfTrades());
        subJson.put("taker_buy_base_asset_volume", document.getTakerBuyBase());
        subJson.put("taker_buy_quote_asset_volume", document.getTakerBuyQuote());
        return subJson;
    }

    public SortedMap<String, Map<String, String>> getCurrentUserDataSet() {
        return currentUserDataSet;
    }

}




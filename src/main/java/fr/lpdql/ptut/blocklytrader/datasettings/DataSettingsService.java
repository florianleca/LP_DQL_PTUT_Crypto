package fr.lpdql.ptut.blocklytrader.datasettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataSettingsService {

    @Autowired
    private KlineRepository klineRepository;

    private SortedMap<String, Map<String, String>> currentUserDataSet;


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
        return klineRepository.findKlinesBetweenDates(startTime, endTime);
    }

    public Map<String, String> extractDataFromSingleLine(KlineDocument document) {
        Map<String, String> subJson = new HashMap<>();
        System.out.println(document.getOpenTime());
//        subJson.put("open", document.getString("open"));
//        subJson.put("high", document.getString("high"));
//        subJson.put("low", document.getString("low"));
//        subJson.put("close", document.getString("close"));
//        subJson.put("volume", document.getString("volume"));
//        subJson.put("close_time", document.getString("close_time"));
//        subJson.put("quote_asset_volume", document.getString("quote_asset_volume"));
//        subJson.put("number_of_trades", document.getString("number_of_trades"));
//        subJson.put("taker_buy_base_asset_volume", document.getString("taker_buy_base_asset_volume"));
//        subJson.put("taker_buy_quote_asset_volume", document.getString("taker_buy_quote_asset_volume"));
        return subJson;
    }

    public SortedMap<String, Map<String, String>> getCurrentUserDataSet() {
        return currentUserDataSet;
    }

}




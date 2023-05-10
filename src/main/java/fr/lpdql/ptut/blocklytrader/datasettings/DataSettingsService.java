package fr.lpdql.ptut.blocklytrader.datasettings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class DataSettingsService {

    @Value("${DB_url}")
    private String url;
    @Value("${DB_user}")
    private String utilisateur;
    @Value("${DB_password}")
    private String motDePasse;
    private SortedMap<String, Map<String, String>> currentUserDataSet;

    public Map<String, Map<String, String>> getJsonFromDataBase(String crypto, String devise, String frequency,
                                                                String startTime, String endTime) throws SQLException {
        String nomDeLaTable = crypto + "_" + devise + "_" + frequency;
        SortedMap<String, Map<String, String>> json = new TreeMap<>();
        ResultSet result = executeDataBaseQuery(nomDeLaTable, startTime, endTime);
        while (result.next()) {
            Map<String, String> subJson = extractDataFromSingleLine(result);
            json.put(result.getString("open_time"), subJson);
        }
        currentUserDataSet = json;
        return json;
    }

    public ResultSet executeDataBaseQuery(String nomDeLaTable, String startTime, String endTime) throws SQLException {
        String requestSQL = "SELECT * FROM " + nomDeLaTable + " WHERE open_time BETWEEN ? AND ?";
        Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
        PreparedStatement preparedStatement = connection.prepareStatement(requestSQL);
        preparedStatement.setLong(1, Long.parseLong(startTime));
        preparedStatement.setLong(2, Long.parseLong(endTime));
        return preparedStatement.executeQuery();
    }

    public Map<String, String> extractDataFromSingleLine(ResultSet result) throws SQLException {
        Map<String, String> subJson = new HashMap<>();
        subJson.put("open", result.getString("open"));
        subJson.put("high", result.getString("high"));
        subJson.put("low", result.getString("low"));
        subJson.put("close", result.getString("close"));
        subJson.put("volume", result.getString("volume"));
        subJson.put("close_time", result.getString("close_time"));
        subJson.put("quote_asset_volume", result.getString("quote_asset_volume"));
        subJson.put("number_of_trades", result.getString("number_of_trades"));
        subJson.put("taker_buy_base_asset_volume", result.getString("taker_buy_base_asset_volume"));
        subJson.put("taker_buy_quote_asset_volume", result.getString("taker_buy_quote_asset_volume"));
        return subJson;
    }

    public SortedMap<String, Map<String, String>> getCurrentUserDataSet() {
        return currentUserDataSet;
    }

}




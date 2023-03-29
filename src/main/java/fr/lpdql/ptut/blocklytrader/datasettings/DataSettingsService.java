package fr.lpdql.ptut.blocklytrader.datasettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataSettingsService {
	
	@Value("${DB_url}")
	private String url;
	
	@Value("${DB_user}")
	private String utilisateur;
	
	@Value("${DB_password}")
	private String motDePasse;

	private Map<String, Map<String, String>> currentUserDataSet;

	public Map<String, Map<String, String>> getJsonFromDataBase(String crypto, String devise, String frequency,
			String startTime, String endTime) throws SQLException {
		String nomDeLaTable = crypto + "_" + devise + "_" + frequency;
		SortedMap<String, Map<String, String>> json = new TreeMap<>();
		ResultSet resultat = executeDataBaseQuery(nomDeLaTable, startTime, endTime);
		while (resultat.next()) {
			Map<String, String> subJson = extractDataFromSingleLine(resultat);
			json.put(resultat.getString("open_time"), subJson);
		}
		currentUserDataSet = json;
		return json;
	}
	
	public ResultSet executeDataBaseQuery(String nomDeLaTable, String startTime, String endTime) throws SQLException {
		String requeteSQL = "SELECT * FROM " + nomDeLaTable + " WHERE open_time BETWEEN ? AND ?";
		Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
		PreparedStatement preparedStatement = connection.prepareStatement(requeteSQL);
		preparedStatement.setLong(1, Long.parseLong(startTime));
		preparedStatement.setLong(2, Long.parseLong(endTime));
		return preparedStatement.executeQuery();
	}

	public Map<String, String> extractDataFromSingleLine(ResultSet resultat) throws SQLException {
		Map<String, String> subJson = new HashMap<>();
		subJson.put("open", resultat.getString("open"));
		subJson.put("high", resultat.getString("high"));
		subJson.put("low", resultat.getString("low"));
		subJson.put("close", resultat.getString("close"));
		subJson.put("volume", resultat.getString("volume"));
		subJson.put("close_time", resultat.getString("close_time"));
		subJson.put("quote_asset_volume", resultat.getString("quote_asset_volume"));
		subJson.put("number_of_trades", resultat.getString("number_of_trades"));
		subJson.put("taker_buy_base_asset_volume", resultat.getString("taker_buy_base_asset_volume"));
		subJson.put("taker_buy_quote_asset_volume", resultat.getString("taker_buy_quote_asset_volume"));
		return subJson;
	}

	public Map<String, Map<String, String>> getCurrentUserDataSet(){
		return currentUserDataSet;
	}
}




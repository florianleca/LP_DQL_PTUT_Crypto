package fr.lpdql.ptut.cryptobotsandbox.testparameters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TestParametersService {

	private String url = "jdbc:mysql://localhost:3306/test";
	private String utilisateur = "root";
	private String motDePasse = "passwordPtut";

	public JSONObject getJsonFromDataBase(String crypto, String devise, String frequency, String startTime,
			String endTime) throws SQLException {
//		String nomDeLaTable = crypto + "_" + devise + "_" + frequency;
		Map<String, Map<String, String>> json = new HashMap<>();
		try (Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
				Statement statement = connexion.createStatement();) {
//			String requeteSQL = "SELECT * FROM " + nomDeLaTable + " WHERE open_time BETWEEN " + startTime + " AND "
//			+ endTime;
			String requeteSQL = "SELECT * FROM bnb_usdt_1m";
			ResultSet resultat = statement.executeQuery(requeteSQL);
			while (resultat.next()) {
				Map<String, String> subJson = extractDataFromSingleLine(resultat);
				json.put(resultat.getString("open_time"), subJson);
			}
		}
		return new JSONObject(json);
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
}

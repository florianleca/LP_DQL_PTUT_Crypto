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

	public JSONObject establishConnection(String crypto, String devise, String frequency, String startTime,
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
		String open = resultat.getString("open");
		subJson.put("open", open);
		String high = resultat.getString("high");
		subJson.put("high", high);
		String low = resultat.getString("low");
		subJson.put("low", low);
		String close = resultat.getString("close");
		subJson.put("close", close);
		String volume = resultat.getString("volume");
		subJson.put("volume", volume);
		String closeTime = resultat.getString("close_time");
		subJson.put("close_time", closeTime);
		String quoteAssetVolume = resultat.getString("quote_asset_volume");
		subJson.put("quote_asset_volume", quoteAssetVolume);
		String numberOfTrades = resultat.getString("number_of_trades");
		subJson.put("number_of_trades", numberOfTrades);
		String takerBuyBaseAssetVolume = resultat.getString("taker_buy_base_asset_volume");
		subJson.put("taker_buy_base_asset_volume", takerBuyBaseAssetVolume);
		String takerBuyQuoteAssetVolume = resultat.getString("taker_buy_quote_asset_volume");
		subJson.put("taker_buy_quote_asset_volume", takerBuyQuoteAssetVolume);
		return subJson;
	}
}

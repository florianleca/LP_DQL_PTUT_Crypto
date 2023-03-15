package fr.lpdql.ptut.cryptobotsandbox.testparameters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;
import org.json.simple.JSONObject;

@Service
public class TestParametersService {

	private String url = "jdbc:mysql://localhost:3306/test";
	private String utilisateur = "root";
	private String motDePasse = "passwordPtut";

	public JSONObject establishConnection() throws SQLException {
		Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
		Statement statement = connexion.createStatement();
		ResultSet resultat = statement.executeQuery("SELECT * FROM bnb_usdt_1m LIMIT 10");
		JSONObject json = new JSONObject();
		while (resultat.next()) {
			String open_time = resultat.getString("open_time");
			String open = resultat.getString("open");
			String high = resultat.getString("high");
			String low = resultat.getString("low");
			String close = resultat.getString("close");
			String volume = resultat.getString("volume");
			String close_time = resultat.getString("close_time");
			String quote_asset_volume = resultat.getString("quote_asset_volume");
			String number_of_trades = resultat.getString("number_of_trades");
			String taker_buy_base_asset_volume = resultat.getString("taker_buy_base_asset_volume");
			String taker_buy_quote_asset_volume = resultat.getString("taker_buy_quote_asset_volume");
			String ignore = resultat.getString("ignore");
			JSONObject subJson = new JSONObject();
			subJson.put("open_time", open_time);
			subJson.put("open", open);
			subJson.put("high", high);
			subJson.put("low", low);
			subJson.put("close", close);
			subJson.put("volume", volume);
			subJson.put("close_time", close_time);
			subJson.put("quote_asset_volume", quote_asset_volume);
			subJson.put("number_of_trades", number_of_trades);
			subJson.put("taker_buy_base_asset_volume", taker_buy_base_asset_volume);
			subJson.put("taker_buy_quote_asset_volume", taker_buy_quote_asset_volume);
			subJson.put("ignore", ignore);
			json.put(open_time, subJson);
		}
		resultat.close();
		statement.close();
		connexion.close();
		return json;
	}

	public static void main(String[] args) throws SQLException {
		TestParametersService tps = new TestParametersService();
		System.out.println(tps.establishConnection());
	}

}

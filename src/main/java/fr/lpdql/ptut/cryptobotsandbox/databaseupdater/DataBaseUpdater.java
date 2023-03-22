package fr.lpdql.ptut.cryptobotsandbox.databaseupdater;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class DataBaseUpdater {
    private Connection conn;
    private OkHttpClient httpClient;
    private final String dbAddress;
	private final String symbol;
	private final String interval;
    private final String tableName;
    private final String dbUser;
    private final String dbPassword;

    public DataBaseUpdater(String dbAddress, String symbol, String interval, String tableName, String dbUser, String dbPassword) throws SQLException {
    	this.dbAddress = dbAddress;
    	this.symbol = symbol;
    	this.interval  = interval;
    	this.tableName = tableName;
    	this.dbUser = dbUser;
    	this.dbPassword = dbPassword;
    	
        // Connexion à la base de données MySQL
        conn = DriverManager.getConnection(dbAddress, dbUser, dbPassword);

        // Initialisation du client HTTP
        httpClient = new OkHttpClient();
    }
    
    public void updateKlines() throws SQLException, JSONException, IOException {
        // Récupération du timestamp du dernier kline dans la base de données
        Statement stmt = conn.createStatement();
        ResultSet resultSet;

        // Boucle pour récupérer les nouvelles klines jusqu'à moins de 10 minutes avant l'heure actuelle
        long now = System.currentTimeMillis();
        long endTime = now - (10 * 60 * 1000); // 10 minutes ago
        long lastTimestamp = 0;
        long startTime = 0;

        while (startTime < endTime) {
            resultSet = stmt.executeQuery(String.format("SELECT MAX(open_time) FROM %s", tableName));
            lastTimestamp = resultSet.getLong(1);
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
                String insertSql = kline.getInsertSql(tableName);
                stmt.executeUpdate(insertSql);
            }
        }
    }
}


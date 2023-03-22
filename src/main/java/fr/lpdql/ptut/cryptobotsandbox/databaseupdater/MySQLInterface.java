package fr.lpdql.ptut.cryptobotsandbox.databaseupdater;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.squareup.okhttp.OkHttpClient;

public class MySQLInterface implements DataBaseInterface {

    private Connection conn;
    private final String dbAddress;
    private final String tableName;
    private final String dbUser;
    private final String dbPassword;
    Statement stmt;

	public MySQLInterface(String dbAddress, String tableName, String dbUser, String dbPassword) throws SQLException {
    	this.dbAddress = dbAddress;
    	this.tableName = tableName;
    	this.dbUser = dbUser;
    	this.dbPassword = dbPassword;
    	
        // Connexion à la base de données MySQL
        conn = DriverManager.getConnection(dbAddress, dbUser, dbPassword);

        stmt = conn.createStatement();
	}
	
	@Override
	public long getLastTimestamp() throws SQLException {
		ResultSet resultSet;
		long lastTimestamp;
		resultSet = stmt.executeQuery(String.format("SELECT MAX(open_time) FROM %s", tableName));
		resultSet.next();//TODO gérer si ca coince
		return resultSet.getLong(1);
	}

	@Override
	public void addKlineToDB(Kline kline) throws SQLException {
		String insertSql = kline.getInsertSql(tableName);
		stmt.executeUpdate(insertSql);
	}

}

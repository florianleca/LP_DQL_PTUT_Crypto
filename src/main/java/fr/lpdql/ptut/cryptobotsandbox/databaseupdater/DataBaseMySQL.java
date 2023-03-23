package fr.lpdql.ptut.cryptobotsandbox.databaseupdater;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseMySQL implements DataBaseInterface {

    private final String tableName;
    Statement stmt;

	public DataBaseMySQL(String dbAddress, String tableName, String dbUser, String dbPassword) throws SQLException {
    	this.tableName = tableName;

        // Connexion à la base de données MySQL
        Connection conn = DriverManager.getConnection(dbAddress, dbUser, dbPassword);
        stmt = conn.createStatement();
	}
	
	@Override
	public long getLastTimestamp() throws SQLException {
		ResultSet resultSet;
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

package fr.lpdql.ptut.blocklytrader.databaseupdater;

import java.sql.*;

public class MySQLConnector implements DataBaseConnector {

    private final String tableName;
    private final Statement statement;

    public MySQLConnector(String dbAddress, String tableName, String dbUser, String dbPassword) throws SQLException {
        this.tableName = tableName;
        // Connexion à la base de données MySQL
        Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPassword);
        statement = connection.createStatement();
    }

    @Override
    public long getLastTimestamp() throws SQLException {
        ResultSet resultSet;
        resultSet = statement.executeQuery(String.format("SELECT MAX(open_time) FROM %s", tableName));
        resultSet.next();//TODO gérer si ça coince
        return resultSet.getLong(1);
    }

    @Override
    public void addKlineToDB(Kline kline) throws SQLException {
        String insertSql = kline.getInsertSql(tableName);
        statement.executeUpdate(insertSql);
    }

}

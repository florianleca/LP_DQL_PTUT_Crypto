package fr.lpdql.ptut.blocklytrader.databaseupdater;

import org.slf4j.Logger;

import java.sql.*;

public class MySQLConnector implements DataBaseConnector {

    public String getTableName() {
        return tableName;
    }

    private final String tableName;
    private final Statement statement;
    private final Logger logger;

    public MySQLConnector(
            Logger logger,
            String dbAddress,
            String tableName,
            String dbUser,
            String dbPassword
    ) throws DataBaseUpdaterException {
        this.logger = logger;
        this.tableName = tableName;
        try {
            // Connexion à la base de données MySQL
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPassword);
            statement = connection.createStatement();
        } catch (SQLException exc){
            throw new DataBaseUpdaterException(exc);
        }
    }

    @Override
    public long getLastTimestamp() throws DataBaseUpdaterException {
        try {
            ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT MAX(open_time) FROM %s", tableName)
            );
            resultSet.next();
            return resultSet.getLong(1);
        }catch(SQLException exc){
            logger.warn("Exception à traiter : " + exc);
            throw new DataBaseUpdaterException(exc);
        }
    }

    @Override
    public void addKlineToDB(Kline kline) throws DataBaseUpdaterException {
        try {
            String insertSql = kline.getInsertSql(tableName);
            statement.executeUpdate(insertSql);
        }catch(SQLException exc){
            logger.warn("Exception à traiter : " + exc);
            throw new DataBaseUpdaterException(exc);
        }
    }

}

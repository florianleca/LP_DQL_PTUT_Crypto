package fr.lpdql.ptut.blocklytrader.databaseupdater;

import java.sql.SQLException;

interface DataBaseConnector {
    //TODO  gérer les exceptions
    long getLastTimestamp() throws SQLException;

    void addKlineToDB(Kline kline) throws SQLException;

}

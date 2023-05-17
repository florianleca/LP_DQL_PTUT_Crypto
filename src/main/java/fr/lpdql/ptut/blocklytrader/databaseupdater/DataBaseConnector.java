package fr.lpdql.ptut.blocklytrader.databaseupdater;


interface DataBaseConnector {

    String getTableName();

    //TODO  gérer les exceptions
    long getLastTimestamp() throws DataBaseUpdaterException;

    void addKlineToDB(Kline kline) throws DataBaseUpdaterException;

}

package fr.lpdql.ptut.cryptobotsandbox.databaseupdater;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

interface DataBaseInterface {
	//TODO  gérer les exeptions
	public long getLastTimestamp() throws SQLException;
	public void addKlineToDB(Kline kline) throws SQLException;
	
}

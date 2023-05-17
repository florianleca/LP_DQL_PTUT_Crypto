package fr.lpdql.ptut.blocklytrader.databaseupdater;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

class MySQLConnectorTest {

    Logger fakeLogger = LoggerFactory.getLogger(MySQLConnectorTest.class);
    String fakeDBAdresse = "fakeDBAdresse";
    String fakeTableName = "fakeTableName";
    String fakeDBUser = "fakeDBUser";
    String fakeDBPassword = "fakeDBPassword";
    MySQLConnector mySQLC;
    Statement mockedStatement;

    @BeforeEach
    void setUp() throws DataBaseUpdaterException {
        mySQLC = new MySQLConnector(
                fakeLogger,
                fakeDBAdresse,
                fakeTableName,
                fakeDBUser,
                fakeDBPassword
        );
        mockedStatement = Mockito.mock(Statement.class);
    }

    @AfterEach
    void tearDown() {
        mySQLC = null;
        mockedStatement = null;
    }

    @Test
    void getLastTimestamp() {
    }

    @Test
    void addKlineToDB() {
    }
}
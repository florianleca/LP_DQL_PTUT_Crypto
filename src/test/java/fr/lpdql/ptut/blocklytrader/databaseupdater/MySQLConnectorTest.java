package fr.lpdql.ptut.blocklytrader.databaseupdater;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Statement;

class MySQLConnectorTest {

    String fakeDBAdresse = "fakeDBAdresse";
    String fakeTableName = "fakeTableName";
    String fakeDBUser = "fakeDBUser";
    String fakeDBPassword = "fakeDBPassword";
    MySQLConnector mySQLC;
    Statement mockedStatement;

    @BeforeEach
    void setUp() {
        mySQLC = new MySQLConnector(
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
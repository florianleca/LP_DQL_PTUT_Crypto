package fr.lpdql.ptut.blocklytrader.databaseupdater;

import org.json.JSONArray;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class DataBaseUpdaterTest {

    private DataBaseConnector mockedDataBaseConnector;
    private ExchangeAPI mockedExchangeAPI;
    private DataBaseUpdater dataBaseUpdater;
    private JSONArray fakeKlinesJson1;
    private JSONArray fakeKlineJson1_1;
    private JSONArray fakeKlineJson1_2;
    private JSONArray fakeKlinesJson2;
    private JSONArray fakeKlineJson2_1;
    private JSONArray fakeKlineJson2_2;
    private Class<Kline> mockedKlineClasse;


    @BeforeEach
    public void setUp() {

        mockedExchangeAPI = mock(ExchangeAPI.class);
        mockedDataBaseConnector = mock(DataBaseConnector.class);
        mockedKlineClasse = (Class<Kline>) mock(Class.class);
        dataBaseUpdater = new DataBaseUpdater(mockedExchangeAPI, mockedDataBaseConnector, mockedKlineClasse);

        fakeKlinesJson1 = spy(new JSONArray());
        fakeKlineJson1_1 = spy(new JSONArray());
        fakeKlineJson1_2 = spy(new JSONArray());
        fakeKlinesJson1.put(fakeKlineJson1_1);
        fakeKlinesJson1.put(fakeKlineJson1_2);
        when(fakeKlinesJson1.length()).thenReturn(2);

        fakeKlinesJson2 = spy(new JSONArray());
        fakeKlineJson2_1 = spy(new JSONArray());
        fakeKlineJson2_2 = spy(new JSONArray());
        fakeKlinesJson2.put(fakeKlineJson2_1);
        fakeKlinesJson2.put(fakeKlineJson2_2);
        when(fakeKlinesJson2.length()).thenReturn(2);
    }
    @AfterEach
    public void TearDown(){
        mockedDataBaseConnector = null;
        mockedExchangeAPI = null;
        dataBaseUpdater = null;

        fakeKlinesJson1 = null;
        fakeKlineJson1_1 = null;
        fakeKlineJson1_2 = null;
        fakeKlinesJson2 = null;
        fakeKlineJson2_1 = null;
        fakeKlineJson2_2 = null;
    }

    @Test
    void updateKlinesOnce() throws DataBaseUpdaterException {


        Mockito.when(mockedDataBaseConnector.getLastTimestamp())
                .thenReturn(99999L)
                .thenReturn(100099L);
        Mockito.when(dataBaseUpdater.getEndTime()).thenReturn(100050L);
        Mockito.when(mockedExchangeAPI.getJSONKlinesFromStartTime(100000L)).thenReturn(fakeKlinesJson1);

        dataBaseUpdater.updateKlines();

        Mockito.verify(mockedDataBaseConnector, times(2)).getLastTimestamp();
        Mockito.verify(mockedExchangeAPI, times(1)).getJSONKlinesFromStartTime(100000L);
        Mockito.verify(mockedDataBaseConnector, times(2)).addKlineToDB(any(Kline.class));
    }

    @Test
    void updateKlinesTwice() throws DataBaseUpdaterException {


        Mockito.when(mockedDataBaseConnector.getLastTimestamp())
                .thenReturn(99999L)
                .thenReturn(100099L)
                .thenReturn(100199L);
        Mockito.when(dataBaseUpdater.getEndTime())
                .thenReturn(100150L);
        Mockito.when(mockedExchangeAPI.getJSONKlinesFromStartTime(100000L)).thenReturn(fakeKlinesJson1);
        Mockito.when(mockedExchangeAPI.getJSONKlinesFromStartTime(100100L)).thenReturn(fakeKlinesJson2);

        dataBaseUpdater.updateKlines();

        Mockito.verify(mockedDataBaseConnector, times(3)).getLastTimestamp();

        Mockito.verify(mockedExchangeAPI, times(1)).getJSONKlinesFromStartTime(100000L);
        Mockito.verify(mockedExchangeAPI, times(1)).getJSONKlinesFromStartTime(100100L);
        Mockito.verify(mockedDataBaseConnector, times(4)).addKlineToDB(any(Kline.class));
    }


    @Test
    void updateNoKlines() throws DataBaseUpdaterException {

        Mockito.when(mockedDataBaseConnector.getLastTimestamp()).thenReturn(100099L);
        Mockito.when(dataBaseUpdater.getEndTime()).thenReturn(100050L);

        dataBaseUpdater.updateKlines();

        Mockito.verify(mockedDataBaseConnector, times(1)).getLastTimestamp();
        Mockito.verify(mockedExchangeAPI, times(0)).getJSONKlinesFromStartTime(100100L);
        Mockito.verify(mockedDataBaseConnector, times(0)).addKlineToDB(any(Kline.class));
    }
}
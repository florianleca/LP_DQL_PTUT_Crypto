package fr.lpdql.ptut.blocklytrader.databaseupdater;

import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
class DataBaseUpdaterTest {

    private DataBaseConnector mockedDataBaseConnector;
    private ExchangeAPI mockedExchangeAPI;
    private DataBaseUpdater spyedBBUpdater;

    @Before
    public void setUp() {
        mockedSystem = mock(System.class);
        mockedExchangeAPI = mock(ExchangeAPI.class);
        mockedDataBaseConnector = mock(DataBaseConnector.class);
        spyedBBUpdater = Mockito.spy(new DataBaseUpdater(
                "fakeSymbol", "fakeInterval", mockedExchangeAPI, mockedDataBaseConnector
        ));
    }
    @After
    public void TearDown(){
        mockedDataBaseConnector = null;
        mockedExchangeAPI = null;
        spyedBBUpdater = null;
    }

    @Test
    void updateKlinesOnce() {
        JSONArray klineReturn = new JSONArray();
        klineReturn.put("fakeKlineValueAlpha");
        klineReturn.put("fakeKlineValueBeta");
        klineReturn.put("fakeKlineValueGamma");

        JSONArray exchangeJsonRetrun = new JSONArray();
        exchangeJsonRetrun.put(klineReturn);
        exchangeJsonRetrun.put(klineReturn);

        Kline fakeKline = new Kline(klineReturn);


        Mockito.when(mockedDataBaseConnector.getLastTimestamp()).thenReturn(99999L);
        Mockito.when(spyedBBUpdater.getEndTime()).thenReturn(100001L);
        Mockito.when(mockedExchangeAPI.getJSONKlinesFromStartTime(100000L)).thenReturn(exchangeJsonRetrun);

        spyedBBUpdater.updateKlines();

        Mockito.verify(mockedDataBaseConnector, times(2)).getLastTimestamp();
        Mockito.verify(mockedExchangeAPI, times(1)).getJSONKlinesFromStartTime(100000L);
        Mockito.verify(mockedDataBaseConnector, times(2)).addKlineToDB(fakeKline);
    }

    @Test
    void updateKlinesTwice() {
        JSONArray klineReturn1 = new JSONArray();
        klineReturn1.put("fakeKlineValueAlpha");
        klineReturn1.put("fakeKlineValueBeta");
        klineReturn1.put("fakeKlineValueGamma");

        JSONArray klineReturn2 = new JSONArray();
        klineReturn2.put("fakeKlineValueAlpha");
        klineReturn2.put("fakeKlineValueBeta");
        klineReturn2.put("fakeKlineValueGamma");

        JSONArray klineReturn3 = new JSONArray();
        klineReturn3.put("fakeKlineValueAlpha");
        klineReturn3.put("fakeKlineValueBeta");
        klineReturn3.put("fakeKlineValueGamma");

        JSONArray klineReturn4 = new JSONArray();
        klineReturn4.put("fakeKlineValueAlpha");
        klineReturn4.put("fakeKlineValueBeta");
        klineReturn4.put("fakeKlineValueGamma");

        JSONArray exchangeJsonRetrun1 = new JSONArray();
        exchangeJsonRetrun1.put(klineReturn1);
        exchangeJsonRetrun1.put(klineReturn2);

        JSONArray exchangeJsonRetrun2 = new JSONArray();
        exchangeJsonRetrun1.put(klineReturn3);
        exchangeJsonRetrun1.put(klineReturn4);

        Kline fakeKline1 = new Kline(klineReturn1);
        Kline fakeKline2 = new Kline(klineReturn2);
        Kline fakeKline3 = new Kline(klineReturn3);
        Kline fakeKline4 = new Kline(klineReturn4);


        Mockito.when(mockedDataBaseConnector.getLastTimestamp())
                .thenReturn(99999L)
                .thenReturn(100099L);
        Mockito.when(spyedBBUpdater.getEndTime())
                .thenReturn(100150L);
        Mockito.when(mockedExchangeAPI.getJSONKlinesFromStartTime(100000L)).thenReturn(exchangeJsonRetrun1);
        Mockito.when(mockedExchangeAPI.getJSONKlinesFromStartTime(100100L)).thenReturn(exchangeJsonRetrun2);

        spyedBBUpdater.updateKlines();

        Mockito.verify(mockedDataBaseConnector, times(3)).getLastTimestamp();

        Mockito.verify(mockedExchangeAPI, times(1)).getJSONKlinesFromStartTime(100000L);
        Mockito.verify(mockedDataBaseConnector, times(1)).addKlineToDB(fakeKline1);
        Mockito.verify(mockedDataBaseConnector, times(1)).addKlineToDB(fakeKline2);

        Mockito.verify(mockedExchangeAPI, times(1)).getJSONKlinesFromStartTime(100100L);
        Mockito.verify(mockedDataBaseConnector, times(1)).addKlineToDB(fakeKline3);
        Mockito.verify(mockedDataBaseConnector, times(2)).addKlineToDB(fakeKline4);
    }


    @Test
    void updateNoKlines() {
        JSONArray klineReturn = new JSONArray();
        klineReturn.put("fakeKlineValueAlpha");
        klineReturn.put("fakeKlineValueBeta");
        klineReturn.put("fakeKlineValueGamma");

        JSONArray exchangeJsonRetrun = new JSONArray();
        exchangeJsonRetrun.put(klineReturn);
        exchangeJsonRetrun.put(klineReturn);

        Kline fakeKline = new Kline(klineReturn);


        Mockito.when(mockedDataBaseConnector.getLastTimestamp()).thenReturn(100099L);
        Mockito.when(spyedBBUpdater.getEndTime()).thenReturn(100050L);

        spyedBBUpdater.updateKlines();

        Mockito.verify(mockedDataBaseConnector, times(1)).getLastTimestamp();
        Mockito.verify(mockedExchangeAPI, times(0)).getJSONKlinesFromStartTime(100100L);
        Mockito.verify(mockedDataBaseConnector, times(0)).addKlineToDB(fakeKline);
    }
}
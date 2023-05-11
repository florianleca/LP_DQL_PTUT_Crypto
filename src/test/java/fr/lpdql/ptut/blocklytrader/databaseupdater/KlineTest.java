package fr.lpdql.ptut.blocklytrader.databaseupdater;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KlineTest {

    Kline kline;
    @BeforeEach
    void setUp() {
        kline = new Kline(1,2,3,4,5,6,7,8,9,10,11,12);
    }

    @AfterEach
    void tearDown() {
        kline = null;
    }

    @Test
    void getInsertSql() {
        assertEquals(
            "INSERT INTO fakeTable "
                    + "(open_time, `open`, high, low, `close`, volume, close_time, "
                    + "quote_asset_volume, number_of_trades, taker_buy_base_asset_volume, "
                    + "taker_buy_quote_asset_volume, `ignore`) "
                    + "VALUES (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)"
                    + ";",
                    kline.getInsertSql("fakeTable")
        );
    }
}
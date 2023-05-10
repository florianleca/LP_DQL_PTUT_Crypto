package fr.lpdql.ptut.blocklytrader.databaseupdater;

import org.json.JSONArray;

import java.util.Locale;

public class Kline {

    private final long openTime;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final double volume;
    private final long closeTime;
    private final double quoteAssetVolume;
    private final int numberOfTrades;
    private final double takerBuyBaseAssetVolume;
    private final double takerBuyQuoteAssetVolume;
    private final int ignore;

    public Kline(JSONArray kline) {
        this.openTime = kline.getLong(0);
        this.open = kline.getDouble(1);
        this.high = kline.getDouble(2);
        this.low = kline.getDouble(3);
        this.close = kline.getDouble(4);
        this.volume = kline.getDouble(5);
        this.closeTime = kline.getLong(6);
        this.quoteAssetVolume = kline.getDouble(7);
        this.numberOfTrades = kline.getInt(8);
        this.takerBuyBaseAssetVolume = kline.getDouble(9);
        this.takerBuyQuoteAssetVolume = kline.getDouble(10);
        this.ignore = kline.getInt(11);
    }

    public String getInsertSql(String tableName) {
        return String.format(new Locale("US"),
                "INSERT INTO %s " + "(open_time, `open`, high, low, `close`, volume, " + "close_time, " +
                        "quote_asset_volume, number_of_trades, taker_buy_base_asset_volume, " +
                        "taker_buy_quote_asset_volume, `ignore`) " + "VALUES (%d, %f, %f, %f, %f, %f, %d, %f, %d, %f," +
                        " %f, %d)" + ";", tableName, this.openTime, this.open, this.high, this.low, this.close,
                this.volume, this.closeTime, this.quoteAssetVolume, this.numberOfTrades, this.takerBuyBaseAssetVolume
                , this.takerBuyQuoteAssetVolume, this.ignore);
    }

}



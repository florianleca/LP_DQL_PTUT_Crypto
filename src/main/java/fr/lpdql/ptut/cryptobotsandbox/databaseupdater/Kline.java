package fr.lpdql.ptut.cryptobotsandbox.databaseupdater;

import org.json.JSONArray;

public class Kline {
    private long openTime;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private long closeTime;
    private double quoteAssetVolume;
    private int numberOfTrades;
    private double takerBuyBaseAssetVolume;
    private double takerBuyQuoteAssetVolume;
    private int ignore;

    public Kline(long openTime, double open, double high, double low, double close, double volume, int closeTime, double quoteAssetVolume,
                 int numberOfTrades, double takerBuyBaseAssetVolume, double takerBuyQuoteAssetVolume, int ignore) {
        this.openTime = openTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.closeTime = closeTime;
        this.quoteAssetVolume = quoteAssetVolume;
        this.numberOfTrades = numberOfTrades;
        this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
        this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
        this.ignore = ignore;
    }

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
        return String.format("INSERT INTO %s VALUES (%d, %f, %f, %f, %f, %f, %d, %f, %d, %f, %f, %d)",
                tableName, this.openTime, this.open, this.high, this.low, this.close, this.volume,
                this.closeTime,
                this.quoteAssetVolume, this.numberOfTrades, this.takerBuyBaseAssetVolume, this.takerBuyQuoteAssetVolume,
        		this.ignore);
    }
}



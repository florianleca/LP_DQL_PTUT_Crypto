package fr.lpdql.ptut.blocklytrader.klines;

import org.json.JSONArray;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "#{@collectionSelector.getCurrentCollection()}")
public class KlineDocument {

    @Field("open_time")
    private String openTime;
    @Field("open")
    private String open;
    @Field("high")
    private String high;
    @Field("low")
    private String low;
    @Field("close")
    private String close;
    @Field("volume")
    private String volume;
    @Field("close_time")
    private String closeTime;
    @Field("quote_asset_volume")
    private String quoteAssetVolume;
    @Field("number_of_trades")
    private String numberOfTrades;
    @Field("taker_buy_base_asset_volume")
    private String takerBuyBase;
    @Field("taker_buy_quote_asset_volume")
    private String takerBuyQuote;

    // utilisé par Spring, ne pas supprimer
    public KlineDocument() {
    }

    public KlineDocument(JSONArray kline) {
        this.openTime = String.valueOf(kline.get(0));
        this.open = String.valueOf(kline.get(1));
        this.high = String.valueOf(kline.get(2));
        this.low = String.valueOf(kline.get(3));
        this.close = String.valueOf(kline.get(4));
        this.volume = String.valueOf(kline.get(5));
        this.closeTime = String.valueOf(kline.get(6));
        this.quoteAssetVolume = String.valueOf(kline.get(7));
        this.numberOfTrades = String.valueOf(kline.get(8));
        this.takerBuyBase = String.valueOf(kline.get(9));
        this.takerBuyQuote = String.valueOf(kline.get(10));
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getClose() {
        return close;
    }

    public String getVolume() {
        return volume;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public String getQuoteAssetVolume() {
        return quoteAssetVolume;
    }

    public String getNumberOfTrades() {
        return numberOfTrades;
    }

    public String getTakerBuyBase() {
        return takerBuyBase;
    }

    public String getTakerBuyQuote() {
        return takerBuyQuote;
    }

    @Override
    public String toString() {
        return "KlineDocument{" + "openTime='" + openTime + '\'' + ", open='" + open + '\'' + ", high='" + high + '\'' + ", low='" + low + '\'' + ", close='" + close + '\'' + ", volume='" + volume + '\'' + ", closeTime='" + closeTime + '\'' + ", quoteAssetVolume='" + quoteAssetVolume + '\'' + ", numberOfTrades='" + numberOfTrades + '\'' + ", takerBuyBase='" + takerBuyBase + '\'' + ", takerBuyQuote='" + takerBuyQuote + '\'' + '}';
    }

}

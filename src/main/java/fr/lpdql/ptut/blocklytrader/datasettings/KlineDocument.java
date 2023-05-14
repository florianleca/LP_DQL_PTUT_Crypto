package fr.lpdql.ptut.blocklytrader.datasettings;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "#{@dataSettingsService.currentCollection}")

public class KlineDocument {

    private String open_time;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    private String closeTime;
    private String quoteAssetVolume;
    private String numberOfTrades;
    private String takerBuyBase;
    private String takerBuyQuote;

    public String getOpenTime() {
        return open_time;
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

}

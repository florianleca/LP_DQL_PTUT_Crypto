package fr.lpdql.ptut.blocklytrader.datasettings;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BTC_USDT_1h")
public class KlineDocument {

    @Id
    private String open_time;
    private double open;

    public String getOpenTime() {
        return open_time;
    }

}

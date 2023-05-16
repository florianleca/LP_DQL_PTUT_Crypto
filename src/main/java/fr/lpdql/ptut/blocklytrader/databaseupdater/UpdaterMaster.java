package fr.lpdql.ptut.blocklytrader.databaseupdater;

import fr.lpdql.ptut.blocklytrader.klines.CollectionSelector;
import fr.lpdql.ptut.blocklytrader.klines.KlineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@EnableScheduling
public class UpdaterMaster {

    private final Logger logger = LoggerFactory.getLogger(UpdaterMaster.class);

    @Autowired
    CollectionSelector collectionSelector;

    @Autowired
    KlineRepository klineRepository;

    @Value("#{'${DB_list_of_tables}'.split(',')}")
    private List<String> collectionNames;
    private List<DataBaseUpdater> updaters;

    public void initializeUpdaters() {
        List<DataBaseUpdater> localUpdaters = new ArrayList<>();
        for (String collectionName : collectionNames) {
            Pattern pattern = Pattern.compile("([^_]+)_([^_]+)_(.+)");
            Matcher matcher = pattern.matcher(collectionName);
            if (matcher.matches()) {
                String crypto = matcher.group(1);
                String currency = matcher.group(2);
                String interval = matcher.group(3);
                String symbol = crypto.toUpperCase() + currency.toUpperCase();
                DataBaseUpdater updater = new DataBaseUpdater(symbol, interval, collectionName, klineRepository, collectionSelector);
                localUpdaters.add(updater);
            }
        }
        this.updaters = localUpdaters;
    }

    // Toutes les tables sont mises à jour toutes les heures
    @Scheduled(fixedRate = 3600 * 1000)
    public void updateAll() {
        logger.info("Database update en cours");
        if (updaters == null) {
            initializeUpdaters();
        }
        for (DataBaseUpdater updater : updaters) {
            try {
                updater.updateKlines();
            } catch (IOException e) {
                //TODO
                logger.warn("Exception à traiter : " + e);
            }
        }
        logger.info("Update terminé");
    }

}

package fr.lpdql.ptut.blocklytrader.databaseupdater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@EnableScheduling
public class UpdaterMaster {

    private final Logger logger = LoggerFactory.getLogger(UpdaterMaster.class);
    @Value("${DB_url}")
    private String url;
    @Value("${DB_user}")
    private String utilisateur;
    @Value("${DB_password}")
    private String motDePasse;
    @Value("#{'${DB_list_of_tables}'.split(',')}")
    private List<String> tablesNames;
    private List<DataBaseUpdater> updaters;

    public void initializeUpdaters() {
        List<DataBaseUpdater> localUpdaters = new ArrayList<>();
        for (String tableName : tablesNames) {
            Pattern pattern = Pattern.compile("([^_]+)_([^_]+)_(.+)");
            Matcher matcher = pattern.matcher(tableName);
            if (matcher.matches()) {
                String crypto = matcher.group(1);
                String currency = matcher.group(2);
                String interval = matcher.group(3);
                String symbol = crypto.toUpperCase() + currency.toUpperCase();
                try {
                    DataBaseUpdater updater = new DataBaseUpdater(
                            new BinanceAPI(
                                    logger,
                                    symbol,
                                    interval
                            ),
                            new MySQLConnector(
                                    logger,
                                    url,
                                    tableName,
                                    utilisateur,
                                    motDePasse
                            )
                    );
                    localUpdaters.add(updater);
                }catch(DataBaseUpdaterException exc){
                    logger.warn(
                        "Erreur lors de l'initialiation de l'updater de la table "+tablesNames+".\n"+
                        "Cette table ne recevra de mise à jours."
                    );
                }
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
            } catch (DataBaseUpdaterException exc) {
                logger.warn(
                        "Erreur lors de la mise à jour de la table "+updater.getTableName()+".\n"
                        +"Cette table n'a pas été mise à jour."
                );
            }
        }
        logger.info("Update terminé");
    }

}

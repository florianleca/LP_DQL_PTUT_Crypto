package fr.lpdql.ptut.blocklytrader.datasettings;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface KlineRepository extends MongoRepository<KlineDocument, String> {

    @Query("{ 'open_time' : { $gte: ?0, $lte: ?1} }")
    List<KlineDocument> findKlinesBetweenDates(float startDate, float endDate);

}

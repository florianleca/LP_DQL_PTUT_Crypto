package fr.lpdql.ptut.blocklytrader.klines;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KlineRepository extends MongoRepository<KlineDocument, String> {

    @Query("{ 'open_time' : { $gte: ?0, $lte: ?1} }")
    List<KlineDocument> findKlinesBetweenDates(String startDate, String endDate);

    KlineDocument findFirstByOrderByOpenTimeDesc();

}

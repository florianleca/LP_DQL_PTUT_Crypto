package fr.lpdql.ptut.blocklytrader.klines;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KlineRepository extends MongoRepository<KlineDocument, String> {

    @Query("{ 'open_time' : { $gte: ?0, $lte: ?1} }")
    List<KlineDocument> findKlinesBetweenDates(float startDate, float endDate);

    @Query(value = "{}", sort = "{'open_time': -1}", fields = "{'open_time': 1}")
    //@Query("{ 'open_time' : { $gte: 1678978251000, $lte: 1681656651000} }")
    KlineDocument findNewestKline();

    KlineDocument findFirstByOrderByOpenTimeDesc();


}

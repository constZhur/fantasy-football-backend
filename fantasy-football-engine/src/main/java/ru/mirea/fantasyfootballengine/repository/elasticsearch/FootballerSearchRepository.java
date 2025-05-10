package ru.mirea.fantasyfootballengine.repository.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.fantasyfootballengine.entity.elasticsearch.FootballerDocument;

import java.util.List;

public interface FootballerSearchRepository extends ElasticsearchRepository<FootballerDocument, Integer> {
    @Query("{\"bool\": { \"should\": [" +
            "  {\"match\": { \"firstName\": { \"query\": \"?0\", \"fuzziness\": \"AUTO\" } }}," +
            "  {\"match\": { \"lastName\": { \"query\": \"?0\", \"fuzziness\": \"AUTO\" } }}," +
            "  {\"multi_match\": { \"query\": \"?0\", \"fields\": [\"firstName\", \"lastName\"], \"type\": \"phrase_prefix\" }}" +
            "]}}")
    List<FootballerDocument> searchByName(String name);
}


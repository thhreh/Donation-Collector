package com.laioffer.donationcollector.repository;

import com.laioffer.donationcollector.entity.Location;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class CustomLocationRepositoryImpl implements CustomLocationRepository{
    private final String DEFAULT_DISTANCE = "50";
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public CustomLocationRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }
    @Override
    public List<Long> searchItemByDistance(double lat, double lon, String distance) {
        if(distance == null || distance.isEmpty()){
            distance = DEFAULT_DISTANCE;
        }
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withFilter(new GeoDistanceQueryBuilder("geoPoint").point(lat, lon).distance(distance, DistanceUnit.KILOMETERS));

        List<Long> itemIds = new ArrayList<>();
        SearchHits<Location> searchHits = elasticsearchOperations.search(queryBuilder.build(), Location.class);
        for(SearchHit<Location> searchHit : searchHits.getSearchHits()){
            itemIds.add(searchHit.getContent().getId());
        }
        return itemIds;
    }
}

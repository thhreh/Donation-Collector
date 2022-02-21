package com.laioffer.donationcollector.repository;

import com.laioffer.donationcollector.entity.Location;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends ElasticsearchRepository<Location, Long> , CustomLocationRepository {
}

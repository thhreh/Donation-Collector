package com.laioffer.donationcollector.service;

import com.laioffer.donationcollector.entity.Item;
import com.laioffer.donationcollector.entity.Location;
import com.laioffer.donationcollector.entity.NGO;
import com.laioffer.donationcollector.repository.ItemRepository;
//import com.laioffer.donationcollector.repository.LocationRepository;
import com.laioffer.donationcollector.repository.LocationRepository;
import com.laioffer.donationcollector.repository.NGORepository;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private ItemRepository itemRepository;
    private NGORepository ngoRepository;
    private GeoEncodingService geoEncodingService;
    private LocationRepository locationRepository;

    @Autowired
    public SearchService(ItemRepository itemRepository, LocationRepository locationRepository, GeoEncodingService geoEncodingService,
                         NGORepository ngoRepository) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.geoEncodingService = geoEncodingService;
        this.ngoRepository = ngoRepository;
    }

    public List<Item> search(String category, Principal principal) {
        //NGO -->principal (logged in)
        NGO ngo = ngoRepository.findById(principal.getName()).orElse(null);
        String distance = ngo.getDistance();
        System.out.println("Distance: " + distance);

        Location ngoLocation = geoEncodingService.getLatLong(1L, ngo.getAddress());
        GeoPoint ngoGeo = ngoLocation.getGeoPoint();

       List<Long> itemIds = locationRepository.searchItemByDistance(ngoGeo.getLat(), ngoGeo.getLon(), distance);
        return itemRepository.findByIdInAndCategory(itemIds, category);
    }

}

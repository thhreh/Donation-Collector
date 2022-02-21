package com.laioffer.donationcollector.service;

import com.laioffer.donationcollector.entity.*;
import com.laioffer.donationcollector.repository.DonorRepository;
import com.laioffer.donationcollector.repository.ItemRepository;
import com.laioffer.donationcollector.repository.LocationRepository;
import com.laioffer.donationcollector.repository.NGORepository;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private DonorRepository donorRepository;
    private ImageStorageService imageStorageService;
    private LocationRepository locationRepository;
    private GeoEncodingService geoEncodingService;
    private NGORepository ngoRepository;
    private SearchService searchService;


    @Autowired
    public ItemService(ItemRepository itemRepository, DonorRepository donorRepository, ImageStorageService imageStorageService, LocationRepository locationRepository, GeoEncodingService geoEncodingService, NGORepository ngoRepository, SearchService searchService)
    {
        this.itemRepository = itemRepository;
        this.donorRepository = donorRepository;
        this.imageStorageService = imageStorageService;
        this.locationRepository = locationRepository;
        this.geoEncodingService = geoEncodingService;
        this.ngoRepository = ngoRepository;
        this.searchService = searchService;
    }

    public Item findByID(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    public void deleteByID(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public List<Item> findByDonor(String username) {
        return itemRepository.findByDonor(new Donor.Builder().setUsername(username).build());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<NGO> add (Item item, MultipartFile[] images) {
        String donorId = item.getDonor().getUsername();
        Donor donor = donorRepository.findById(donorId).orElse(null);
        item.setDonor(donor);
        item.setAddress(donor.getAddress());

        List<String> mediaLinks = Arrays.stream(images).parallel()
                .map(image -> imageStorageService.save(image)).collect(Collectors.toList());
        List<ItemImage> itemImages = new ArrayList<>();
        for (String mediaLink : mediaLinks) {
            itemImages.add(new ItemImage(mediaLink, item));
        }
        item.setImages(itemImages);
        //save item
        itemRepository.save(item);
        System.out.println("ITEM ID: " + item.getId());
        Location location = geoEncodingService.getLatLong(item.getId(), donor.getAddress());
        //save item location
        System.out.println("LOCATION : " + item.getId());
        locationRepository.save(location);
        //find NGO with same category

        List<NGO> prefHit = new ArrayList<>();
        List<NGO> sameCategoryNGO = ngoRepository.findByPrefCategory(item.getCategory());
        //find if item added matches NGO distance
        for(NGO a : sameCategoryNGO) {
            System.out.println(a.getUsername());
            Location ngoLocation = geoEncodingService.getLatLong(1L, a.getAddress());
            GeoPoint ngoGeo = ngoLocation.getGeoPoint();
            List<Long> itemIds = locationRepository.searchItemByDistance(ngoGeo.getLat(), ngoGeo.getLon(), a.getDistance());
            boolean distanceMatch = false;
            for(Long itemId : itemIds){
                if(itemId == item.getId()) {
                    distanceMatch = true;
                    break;
                }
            }
            if(distanceMatch){
                double cur = a.getCurrentWeight();
                cur += item.getWeight();
                ngoRepository.updateCurrentWeight(a.getUsername(),cur);
                if(cur >= a.getPreferredWeight()){
                    prefHit.add(a);
                }
            }
        }
        return prefHit;
    }

}

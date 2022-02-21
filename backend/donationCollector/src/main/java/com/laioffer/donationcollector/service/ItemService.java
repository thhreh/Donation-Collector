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

import java.security.Principal;
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


    @Autowired
    public ItemService(ItemRepository itemRepository, DonorRepository donorRepository, ImageStorageService imageStorageService, LocationRepository locationRepository, GeoEncodingService geoEncodingService, NGORepository ngoRepository)
    {
        this.itemRepository = itemRepository;
        this.donorRepository = donorRepository;
        this.imageStorageService = imageStorageService;
        this.locationRepository = locationRepository;
        this.geoEncodingService = geoEncodingService;
        this.ngoRepository = ngoRepository;
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
    public List<NGO> add (Item item, MultipartFile[] images, Principal principal) {

        Donor donor = donorRepository.findById(principal.getName()).orElse(null);
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
        //find NGO with same category
        List<NGO> prefHit = new ArrayList<>();
        List<NGO> sameCategoryNGO = ngoRepository.findByPrefCategory(item.getCategory());
        //find if item added matches NGO distance
        for(NGO a : sameCategoryNGO) {
            System.out.println(a.getUsername());
            Location ngoLocation = geoEncodingService.getLatLong(1L, a.getAddress());
            GeoPoint ngoGeo = ngoLocation.getGeoPoint();
            List<Long> itemIds = locationRepository.searchItemByDistance(ngoGeo.getLat(), ngoGeo.getLon(), a.getDistance());
            List<Item> filteredItems = itemRepository.findByIdInAndCategory(itemIds, item.getCategory());
            double sumOfAllMatches = 0;
            for(Item itemOne : filteredItems){
                sumOfAllMatches += itemOne.getWeight();
            }
            ngoRepository.updateCurrentWeight(a.getUsername(),sumOfAllMatches);
            if(sumOfAllMatches >= a.getPrefWeight()){
                prefHit.add(a);
            }
        }

        return prefHit;
    }

}

package com.laioffer.donationcollector.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.laioffer.donationcollector.entity.Location;
import com.laioffer.donationcollector.exception.GeoEncodingException;
import com.laioffer.donationcollector.exception.InvalidItemAddressException;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeoEncodingService {
    @Value("${geocoding.apikey}")
    private String apikey;

    public Location getLatLong(Long id, String itemAddress) { //0
        GeoApiContext context = new GeoApiContext.Builder().apiKey(apikey).build();
        try {
            GeocodingResult result = GeocodingApi.geocode(context, itemAddress).await()[0];
            if (result.partialMatch) {
                throw new InvalidItemAddressException("Failed to find item address");
            }
            return new Location(id, new GeoPoint(result.geometry.location.lat, result.geometry.location.lng));
        } catch (IOException | ApiException | InterruptedException e) {
            e.printStackTrace();
            throw new GeoEncodingException("Failed to encode item address");
        }
    }

}

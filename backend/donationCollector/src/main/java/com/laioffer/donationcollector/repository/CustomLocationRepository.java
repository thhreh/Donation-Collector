package com.laioffer.donationcollector.repository;

import java.util.List;

public interface CustomLocationRepository {
    List<Long> searchItemByDistance (double lat, double lon, String distance);
}

package com.laioffer.donationcollector.repository;

import com.laioffer.donationcollector.entity.Donor;
import com.laioffer.donationcollector.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    //JPA already provides findById, deleteByid, and save
    //we need findByDonor to support ListItems function for Donor
    List<Item> findByDonor(Donor donor);
    List<Item> findByIdInAndCategory(List<Long> ids, String category);

}

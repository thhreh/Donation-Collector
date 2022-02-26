package com.laioffer.donationcollector.repository;

import com.laioffer.donationcollector.entity.CartItem;
import com.laioffer.donationcollector.entity.Donor;
import com.laioffer.donationcollector.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    //JPA already provides findById, deleteByid, and save
    //we need findByDonor to support ListItems function for Donor
    List<Item> findByDonor(Donor donor);
    List<Item> findByIdInAndCategory(List<Long> ids, String category);
    /*
    @Modifying
    @Query("UPDATE Item i SET i.cartItems = :cartItems WHERE i.id = :id")
    void updateCartItem(@Param("id") Long id, @Param("cartItems") List<CartItem> cartItems);
    */
}

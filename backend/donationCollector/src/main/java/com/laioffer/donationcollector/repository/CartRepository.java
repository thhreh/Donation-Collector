package com.laioffer.donationcollector.repository;

import com.laioffer.donationcollector.entity.Cart;
import com.laioffer.donationcollector.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Modifying
    @Query("UPDATE Cart c SET c.cartItemList = ?2 WHERE c.id = ?1")
    void updateCartList(Long cartId, List<CartItem> cartItems);

}

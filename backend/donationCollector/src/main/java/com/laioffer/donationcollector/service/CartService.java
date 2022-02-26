package com.laioffer.donationcollector.service;

import com.laioffer.donationcollector.entity.*;
import com.laioffer.donationcollector.repository.CartItemRepository;
import com.laioffer.donationcollector.repository.CartRepository;
import com.laioffer.donationcollector.repository.ItemRepository;
import com.laioffer.donationcollector.repository.NGORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CartService {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;
    private NGORepository ngoRepository;
    private CartItemRepository cartItemRepository;

    @Autowired
    public CartService(ItemRepository itemRepository, CartRepository cartRepository, NGORepository ngoRepository, CartItemRepository cartItemRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.ngoRepository = ngoRepository;
        this.cartItemRepository = cartItemRepository;
    }

    // returns a lsite of donors to be alerted
    public List<Donor> scheduled(Principal principal) {
        Set<Donor> donorSet = new HashSet<>();
        NGO ngo = ngoRepository.findById(principal.getName()).orElse(null);
        Cart cart = ngo.getCart();
        List<CartItem> cartItems = cart.getCartItemList();
        List<CartItem> items = new ArrayList<>(cartItems);
        cart.setCartItemList(new ArrayList<>());
        for(CartItem cartItem: items) {
            donorSet.add(getDonor(cartItem, cartItems));
            removeCartItem(cartItem, cartItems);
            Item cur = cartItem.getItem();
            itemRepository.deleteById(cur.getId());
        }
        return new ArrayList<>(donorSet);
    }

    public Donor getDonor(CartItem cartItem, List<CartItem> cartItems) {
        Item item = cartItem.getItem();
        Donor donor = item.getDonor();
        return donor;
    }

    public void removeCartItem(CartItem cartItem, List<CartItem> cartItems) {
        cartItemRepository.deleteById(cartItem.getId());
    }


    public Cart getCart(Principal principal) {
        NGO ngo = ngoRepository.findById(principal.getName()).orElse(null);
        Cart cart = ngo.getCart();
        double totalWeight = 0;
        for(CartItem cartItem: cart.getCartItemList()) {
            totalWeight += cartItem.getWeight();
            System.out.println(cartItem.getId() + ":" + cartItem.getWeight());
        }
        System.out.println("total weight:"+ totalWeight);
        cart.setTotalWeight(totalWeight);
        return cart;
    }
}


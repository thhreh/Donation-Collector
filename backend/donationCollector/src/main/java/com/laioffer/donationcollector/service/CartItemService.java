package com.laioffer.donationcollector.service;

import com.laioffer.donationcollector.entity.CartItem;
import com.laioffer.donationcollector.entity.Item;
import com.laioffer.donationcollector.entity.NGO;
import com.laioffer.donationcollector.repository.CartItemRepository;
import com.laioffer.donationcollector.repository.NGORepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class CartItemService {
    NGORepository ngoRepository;
    CartItemRepository cartItemRepository;
    ItemService itemService;

    public CartItemService(NGORepository ngoRepository, CartItemRepository cartItemRepository, ItemService itemService) {
        this.ngoRepository = ngoRepository;
        this.cartItemRepository = cartItemRepository;
        this.itemService = itemService;
    }

    public void addItemToCart(Long itemId, Principal principal) {
        NGO ngo = ngoRepository.findById(principal.getName()).orElse(null);
        final CartItem cartItem = new CartItem();
        Item item = itemService.findByID(itemId);
        cartItem.setCart(ngo.getCart());
        cartItem.setItem(item);
        cartItem.setWeight(item.getWeight());
        cartItemRepository.save(cartItem);
        // id ,weight, cart id, item id
    }


}


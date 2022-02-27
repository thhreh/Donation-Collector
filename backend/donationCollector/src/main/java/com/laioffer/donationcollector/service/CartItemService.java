package com.laioffer.donationcollector.service;

import com.laioffer.donationcollector.entity.Cart;
import com.laioffer.donationcollector.entity.CartItem;
import com.laioffer.donationcollector.entity.Item;
import com.laioffer.donationcollector.entity.NGO;
import com.laioffer.donationcollector.repository.CartItemRepository;
import com.laioffer.donationcollector.repository.ItemRepository;
import com.laioffer.donationcollector.repository.NGORepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
public class CartItemService {
    NGORepository ngoRepository;
    CartItemRepository cartItemRepository;
    ItemService itemService;
    ItemRepository itemRepository;

    public CartItemService(NGORepository ngoRepository, CartItemRepository cartItemRepository, ItemService itemService, ItemRepository itemRepository) {
        this.ngoRepository = ngoRepository;
        this.cartItemRepository = cartItemRepository;
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addItemToCart(Long itemId, Principal principal) {
        NGO ngo = ngoRepository.findById(principal.getName()).orElse(null);
        final CartItem cartItem = new CartItem();
        Item item = itemService.findByID(itemId);
        cartItem.setCart(ngo.getCart());
        cartItem.setItem(item);
        cartItem.setWeight(item.getWeight());
        cartItemRepository.save(cartItem);
        /*
        List<CartItem> cur = item.getCartItem();
        cur.add(cartItem);
        itemRepository.save(item);
        */
        // id ,weight, cart id, item id
    }


}


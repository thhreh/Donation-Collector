package com.laioffer.donationcollector.controller;

import com.laioffer.donationcollector.entity.Cart;
import com.laioffer.donationcollector.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(value = "/cart")
    public Cart getCart(Principal principal){
        return cartService.getCart(principal);
    }


}
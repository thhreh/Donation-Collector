package com.laioffer.donationcollector.controller;

import com.laioffer.donationcollector.entity.Donor;
import com.laioffer.donationcollector.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SchedulePickUpController {
    private CartService cartService;

    @Autowired
    public SchedulePickUpController(CartService cartService) {
        this.cartService = cartService;
    }

    @DeleteMapping("/schedule")
    public List<Donor> scheduled(Principal principal) {
        List<Donor> donors = cartService.scheduled(principal);
        return donors;
    }


}

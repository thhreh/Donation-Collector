package com.laioffer.donationcollector.controller;

import com.laioffer.donationcollector.entity.Donor;
import com.laioffer.donationcollector.entity.Item;
import com.laioffer.donationcollector.entity.NGO;
import com.laioffer.donationcollector.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }
    @GetMapping(value = "/items")
    public List<Item> listItems(Principal principal) {
        return itemService.findByDonor(principal.getName());
    }
    @GetMapping(value = "/items/{itemId}")
    public Item getItem(@PathVariable Long itemId) {
        return itemService.findByID(itemId);
    }

    @PostMapping("/items")
    public List<NGO> addItem(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("donor") String donor,
            @RequestParam("weight") double weight,
            @RequestParam("images") MultipartFile[] images,
            Principal principal) {

        Item item = new Item.Builder().setName(name)
                .setDescription(description)
                .setWeight(weight)
                .setCategory(category)
                .setDonor(new Donor.Builder().setUsername(donor).build())
                .build();
       List<NGO> ngosToBeNotified = itemService.add(item, images, principal);
       return ngosToBeNotified;
    }

    @DeleteMapping("/items/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteByID(itemId);
    }
}

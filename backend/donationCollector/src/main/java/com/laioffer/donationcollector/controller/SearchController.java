package com.laioffer.donationcollector.controller;

import com.laioffer.donationcollector.entity.Item;
import com.laioffer.donationcollector.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {
    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "/search")
    public List<Item> searchItems(@RequestParam("category") String category, Principal principal) {
        return searchService.search(category, principal);
    }
}

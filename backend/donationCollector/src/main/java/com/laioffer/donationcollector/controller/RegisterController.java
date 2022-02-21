package com.laioffer.donationcollector.controller;

import com.laioffer.donationcollector.entity.Donor;
import com.laioffer.donationcollector.entity.NGO;
import com.laioffer.donationcollector.entity.UserRole;
import com.laioffer.donationcollector.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {

        this.registerService = registerService;
    }
    @PostMapping("/register/donor")
    public void addDonor(@RequestBody Donor donor) {
        registerService.addDonor(donor, UserRole.ROLE_DONOR);
    }

    @PostMapping("/register/ngo")
    public void addNGO(@RequestBody NGO ngo) {
        registerService.addNGO(ngo, UserRole.ROLE_NGO);
    }
}

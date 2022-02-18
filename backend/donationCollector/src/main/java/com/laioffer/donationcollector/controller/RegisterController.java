package com.laioffer.donationcollector.controller;

import com.laioffer.donationcollector.entity.Donor;
import com.laioffer.donationcollector.entity.NGO;
import com.laioffer.donationcollector.entity.UserRole;
import com.laioffer.donationcollector.service.NGORegisterService;
import com.laioffer.donationcollector.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private RegisterService registerService;
    private NGORegisterService ngoRegisterService;


    @Autowired
    public RegisterController(RegisterService registerService, NGORegisterService ngoRegisterService) {
        this.ngoRegisterService = ngoRegisterService;
        this.registerService = registerService;
    }
    @PostMapping("/register/donor")
    public void addGuest(@RequestBody Donor donor) {
        registerService.add(donor, UserRole.ROLE_DONOR);
    }

    @PostMapping("/register/ngo")
    public void addHost(@RequestBody NGO ngo) {
        ngoRegisterService.add(ngo, UserRole.ROLE_NGO);
    }

}
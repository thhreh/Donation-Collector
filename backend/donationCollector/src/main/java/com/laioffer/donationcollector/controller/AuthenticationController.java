package com.laioffer.donationcollector.controller;

import com.laioffer.donationcollector.entity.Donor;
import com.laioffer.donationcollector.entity.NGO;
import com.laioffer.donationcollector.entity.Token;
import com.laioffer.donationcollector.entity.UserRole;
import com.laioffer.donationcollector.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate/donor")
    public Token authenticateGuest(@RequestBody Donor donor) {
        return authenticationService.authenticate(donor, UserRole.ROLE_DONOR);
    }

    @PostMapping("/authenticate/ngo")
    public Token authenticateHost(@RequestBody NGO ngo) {
        return authenticationService.authenticate(ngo, UserRole.ROLE_NGO);
    }
}

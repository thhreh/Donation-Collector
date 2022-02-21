package com.laioffer.donationcollector.controller;

import com.laioffer.donationcollector.entity.*;
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
        String username;
        String password;
        username = donor.getUsername();
        password = donor.getPassword();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return authenticationService.authenticate(user, UserRole.ROLE_DONOR);
    }

    @PostMapping("/authenticate/ngo")
    public Token authenticateHost(@RequestBody NGO ngo) {
        String username;
        String password;
        username = ngo.getUsername();
        password = ngo.getPassword();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return authenticationService.authenticate(user, UserRole.ROLE_NGO);
    }
}

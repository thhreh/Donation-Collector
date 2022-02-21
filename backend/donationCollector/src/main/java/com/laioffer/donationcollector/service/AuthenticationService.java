package com.laioffer.donationcollector.service;

import com.laioffer.donationcollector.entity.*;
import com.laioffer.donationcollector.exception.DonorNotExistException;
import com.laioffer.donationcollector.exception.NGONotExistException;
import com.laioffer.donationcollector.exception.UserNotExistException;
import com.laioffer.donationcollector.repository.AuthorityRepository;
import com.laioffer.donationcollector.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private AuthorityRepository authorityRepository;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, AuthorityRepository authorityRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.authorityRepository = authorityRepository;
        this.jwtUtil = jwtUtil;
    }

    public Token authenticate(User user, UserRole role) throws UserNotExistException {
        System.out.println("User!!!!!!!!" + user.getUsername() + " " + user.getPassword());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (AuthenticationException exception) {
            throw new UserNotExistException("User Doesn't Exist");
        }

        Authority authority = authorityRepository.findById(user.getUsername()).orElse(null);
        if (!authority.getAuthority().equals(role.name())) {
            throw new UserNotExistException("User Doesn't Exist");
        }

        return new Token(jwtUtil.generateToken(user.getUsername()));
    }

//    public Token authenticate(Donor donor, UserRole role) throws DonorNotExistException {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(donor.getUsername(), donor.getPassword()));
//        } catch (AuthenticationException exception) {
//            throw new DonorNotExistException("Donor Doesn't Exist");
//        }
//
//        Authority authority = authorityRepository.findById(donor.getUsername()).orElse(null);
//        if (!authority.getAuthority().equals(role.name())) {
//            throw new DonorNotExistException("User Doesn't Exist");
//        }
//
//        return new Token(jwtUtil.generateToken(donor.getUsername()));
//    }

//    public Token authenticate(NGO ngo, UserRole role) throws NGONotExistException {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(ngo.getUsername(), ngo.getPassword()));
//        } catch (AuthenticationException exception) {
//            throw new DonorNotExistException("NGO Doesn't Exist");
//        }
//
//        Authority authority = authorityRepository.findById(ngo.getUsername()).orElse(null);
//        if (!authority.getAuthority().equals(role.name())) {
//            throw new DonorNotExistException("NGO Doesn't Exist");
//        }
//
//        return new Token(jwtUtil.generateToken(ngo.getUsername()));
//    }


}


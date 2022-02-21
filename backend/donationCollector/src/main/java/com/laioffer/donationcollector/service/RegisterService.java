package com.laioffer.donationcollector.service;

import com.laioffer.donationcollector.entity.*;
import com.laioffer.donationcollector.exception.UserAlreadyExistException;
import com.laioffer.donationcollector.repository.AuthorityRepository;
import com.laioffer.donationcollector.repository.DonorRepository;
import com.laioffer.donationcollector.repository.NGORepository;
import com.laioffer.donationcollector.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private DonorRepository donorRepository;
    private NGORepository ngoRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public RegisterService(DonorRepository donorRepository, NGORepository ngoRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, UserRepository userRepository) {
        this.donorRepository = donorRepository;
        this.ngoRepository = ngoRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addDonor(Donor donor, UserRole role) throws UserAlreadyExistException {
        if(donorRepository.existsById(donor.getUsername())) {
            throw new UserAlreadyExistException("Username already exists.");
        }

        donor.setPassword(passwordEncoder.encode(donor.getPassword()));
        donor.setEnabled(true);
        String username;
        String password;
        username = donor.getUsername();
        password = donor.getPassword();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);
        userRepository.save(user);;
        donorRepository.save(donor);
        authorityRepository.save(new Authority(donor.getUsername(), role.name()));
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addNGO(NGO ngo, UserRole role) throws UserAlreadyExistException {
        if(ngoRepository.existsById(ngo.getUsername())) {
            throw new UserAlreadyExistException("Username already exists.");
        }

        ngo.setPassword(passwordEncoder.encode(ngo.getPassword()));
        ngo.setEnabled(true);
        ngo.setCurrentWeight(0);
        Cart cart = new Cart();
        ngo.setCart(cart);
        String username;
        String password;
        username = ngo.getUsername();
        password = ngo.getPassword();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);
        userRepository.save(user);;
        ngoRepository.save(ngo);
        authorityRepository.save(new Authority(ngo.getUsername(), role.name()));
    }

}

package com.laioffer.donationcollector.service;

import com.laioffer.donationcollector.entity.Authority;
import com.laioffer.donationcollector.entity.Donor;
import com.laioffer.donationcollector.entity.UserRole;
import com.laioffer.donationcollector.exception.DonorAlreadyExistException;
import com.laioffer.donationcollector.repository.AuthorityRepository;
import com.laioffer.donationcollector.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private DonorRepository donorRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;



    @Autowired
    public RegisterService(DonorRepository donorRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.donorRepository = donorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)// serializable一个一个执行保证原子性，最高级
    public void add(Donor donor, UserRole role) throws DonorAlreadyExistException {
        if (donorRepository.existsById(donor.getUsername())) {
            throw new DonorAlreadyExistException("Donor already exists");
        }

        donor.setPassword(passwordEncoder.encode(donor.getPassword()));
        donor.setEnabled(true);
        donorRepository.save(donor);
        authorityRepository.save(new Authority(donor.getUsername(), role.name()));
    }
}

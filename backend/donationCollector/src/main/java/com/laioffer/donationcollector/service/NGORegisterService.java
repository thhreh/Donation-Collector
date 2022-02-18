package com.laioffer.donationcollector.service;

import com.laioffer.donationcollector.entity.Authority;
import com.laioffer.donationcollector.entity.NGO;
import com.laioffer.donationcollector.entity.UserRole;
import com.laioffer.donationcollector.exception.DonorAlreadyExistException;
import com.laioffer.donationcollector.repository.AuthorityRepository;

import com.laioffer.donationcollector.repository.NGORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NGORegisterService {

    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private NGORepository ngoRepository;




    @Autowired
    public NGORegisterService(NGORepository ngoRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.ngoRepository = ngoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)// serializable一个一个执行保证原子性，最高级
    public void add(NGO ngo, UserRole role) throws DonorAlreadyExistException {
        if (ngoRepository.existsById(ngo.getUsername())) {
            throw new DonorAlreadyExistException("NGO already exists");
        }

        ngo.setPassword(passwordEncoder.encode(ngo.getPassword()));
        ngo.setEnabled(true);
        ngoRepository.save(ngo);
        authorityRepository.save(new Authority(ngo.getUsername(), role.name()));
    }
}

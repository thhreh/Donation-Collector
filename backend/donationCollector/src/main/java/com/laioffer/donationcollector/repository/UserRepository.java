package com.laioffer.donationcollector.repository;

import com.laioffer.donationcollector.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

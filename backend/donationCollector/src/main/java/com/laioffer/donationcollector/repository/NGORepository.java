package com.laioffer.donationcollector.repository;

import com.laioffer.donationcollector.entity.Donor;
import com.laioffer.donationcollector.entity.NGO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NGORepository extends JpaRepository<NGO, String> {


}
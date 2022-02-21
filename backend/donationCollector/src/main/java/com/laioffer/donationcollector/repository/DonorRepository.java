package com.laioffer.donationcollector.repository;

import com.laioffer.donationcollector.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepository extends JpaRepository<Donor, String> {
}

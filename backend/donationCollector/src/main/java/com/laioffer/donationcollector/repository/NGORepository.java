package com.laioffer.donationcollector.repository;


import com.laioffer.donationcollector.entity.NGO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NGORepository extends JpaRepository<NGO, String> {
    List<NGO> findByPrefCategory(String category);

    @Modifying
    @Query("UPDATE NGO n SET n.currentWeight = :currentWeight WHERE n.username = :username")
    void updateCurrentWeight(@Param("username") String username, @Param("currentWeight") double currentWeight);

}


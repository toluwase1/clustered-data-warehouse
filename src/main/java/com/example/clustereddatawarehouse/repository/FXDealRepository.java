package com.example.clustereddatawarehouse.repository;

import com.example.clustereddatawarehouse.entity.FXDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FXDealRepository extends JpaRepository<FXDeal,Integer> {
    boolean existsByDealUniqueId(String dealUniqueId);
}

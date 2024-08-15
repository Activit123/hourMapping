package com.mihai.Java_2024.features.revenueFeature.repository;

import com.mihai.Java_2024.features.revenueFeature.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RevenueRepository extends JpaRepository<Revenue,Integer> {
    Optional<List<Revenue>> findRevenuesByCategoryId(Integer category_id);
    List<Revenue> findByUserId(Integer user_id);
}

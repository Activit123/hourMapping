package com.mihai.Java_2024.features.rateFeature.repository;

import com.mihai.Java_2024.features.rateFeature.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate,Integer> {
    Optional<Rate> findByCategoryId(int categoryId);
}

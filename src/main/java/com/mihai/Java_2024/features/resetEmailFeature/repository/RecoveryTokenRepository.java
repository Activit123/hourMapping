package com.mihai.Java_2024.features.resetEmailFeature.repository;

import com.mihai.Java_2024.features.resetEmailFeature.entity.RecoveryToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecoveryTokenRepository extends JpaRepository<RecoveryToken, Long> {
    RecoveryToken findByToken(String token);
}

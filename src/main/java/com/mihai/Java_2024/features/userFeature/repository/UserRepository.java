package com.mihai.Java_2024.features.userFeature.repository;

import com.mihai.Java_2024.features.userFeature.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findUserByEmail(String userEmail);
}

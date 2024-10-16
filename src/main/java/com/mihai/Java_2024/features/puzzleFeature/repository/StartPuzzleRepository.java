package com.mihai.Java_2024.features.puzzleFeature.repository;

import com.mihai.Java_2024.features.puzzleFeature.entity.Puzzle;
import com.mihai.Java_2024.features.puzzleFeature.entity.StartPuzzle;
import com.mihai.Java_2024.features.userFeature.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StartPuzzleRepository extends JpaRepository<StartPuzzle, Integer> {
    Optional<StartPuzzle> findByUserAndPuzzle(User user, Puzzle puzzle);
    Optional<StartPuzzle> findByUserAndPuzzleAndFinishTimeIsNull(User user, Puzzle puzzle);
}
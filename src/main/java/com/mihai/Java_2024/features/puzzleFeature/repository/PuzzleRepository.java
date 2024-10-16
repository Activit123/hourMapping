package com.mihai.Java_2024.features.puzzleFeature.repository;

import com.mihai.Java_2024.features.puzzleFeature.entity.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, Integer> {
    @Query(value = "SELECT * FROM puzzle WHERE id > :currentPuzzleId ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Optional<Puzzle> findNextPuzzle(int currentPuzzleId);
}
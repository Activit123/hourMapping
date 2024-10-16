package com.mihai.Java_2024.features.puzzleFeature.controller;

import com.mihai.Java_2024.features.puzzleFeature.dto.PuzzleDTO;
import com.mihai.Java_2024.features.puzzleFeature.entity.Puzzle;
import com.mihai.Java_2024.features.puzzleFeature.service.PuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/puzzles")
public class PuzzleController {

    @Autowired
    private PuzzleService puzzleService;
    // Endpoint to check if the provided solution matches the correct one
    @PostMapping("/{puzzleId}/check-solution")
    public ResponseEntity<?> checkSolution(
            @PathVariable int puzzleId,
            @RequestParam String solution) {

        return puzzleService.checkPuzzleSolution(puzzleId, solution);
    }
    // Create a new puzzle
    @PostMapping
    public ResponseEntity<PuzzleDTO> createPuzzle(@RequestBody PuzzleDTO puzzleDTO) {
        Puzzle puzzle = puzzleService.createPuzzle(puzzleDTO);
        return ResponseEntity.ok(puzzleService.toDTO(puzzle));
    }

    // Get all puzzles
    @GetMapping
    public ResponseEntity<List<PuzzleDTO>> getAllPuzzles() {
        List<PuzzleDTO> puzzles = puzzleService.getAllPuzzles();
        return ResponseEntity.ok(puzzles);
    }

    // Get puzzle by ID
    @GetMapping("/{id}")
    public ResponseEntity<PuzzleDTO> getPuzzleById(@PathVariable int id) {
        Optional<PuzzleDTO> puzzle = puzzleService.getPuzzleById(id);
        return puzzle.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update puzzle by ID
    @PutMapping("/{id}")
    public ResponseEntity<PuzzleDTO> updatePuzzle(@PathVariable int id, @RequestBody PuzzleDTO puzzleDTO) {
        Optional<PuzzleDTO> updatedPuzzle = puzzleService.updatePuzzle(id, puzzleDTO);
        return updatedPuzzle.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete puzzle by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePuzzle(@PathVariable int id) {
        boolean deleted = puzzleService.deletePuzzle(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
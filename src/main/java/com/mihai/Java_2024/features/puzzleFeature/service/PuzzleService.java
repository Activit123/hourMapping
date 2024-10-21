package com.mihai.Java_2024.features.puzzleFeature.service;

import com.mihai.Java_2024.config.ContextHolderService;
import com.mihai.Java_2024.features.puzzleFeature.dto.PuzzleDTO;
import com.mihai.Java_2024.features.puzzleFeature.dto.ShowPuzzle;
import com.mihai.Java_2024.features.puzzleFeature.entity.Puzzle;
import com.mihai.Java_2024.features.puzzleFeature.repository.PuzzleRepository;
import com.mihai.Java_2024.features.userFeature.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PuzzleService {
    @Autowired
    private StartPuzzleService startPuzzleService;
    @Autowired
    private PuzzleRepository puzzleRepository;
    @Autowired
    private ContextHolderService contextHolderService;
    // Create Puzzle
    public Puzzle createPuzzle(PuzzleDTO puzzleDTO) {
        Puzzle puzzle = new Puzzle();
        puzzle.setPname(puzzleDTO.getPname());
        puzzle.setSolution(puzzleDTO.getSolution());
        return puzzleRepository.save(puzzle);
    }

    // Convert Entity to DTO
    public PuzzleDTO toDTO(Puzzle puzzle) {
        PuzzleDTO dto = new PuzzleDTO();
        dto.setPname(puzzle.getPname());
        dto.setSolution(puzzle.getSolution());
        return dto;
    }

    // Convert Entity to ShowPuzzle DTO
    public ShowPuzzle toDTO2(Puzzle puzzle) {
        ShowPuzzle dto = new ShowPuzzle();
        dto.setPuzzleId(puzzle.getId()); // Assuming Puzzle entity has puzzleId
        dto.setPname(puzzle.getPname());
        return dto;
    }


    // Convert DTO to Entity
    public Puzzle fromDTO(PuzzleDTO dto) {
        Puzzle puzzle = new Puzzle();
        puzzle.setPname(dto.getPname());
        puzzle.setSolution(dto.getSolution());
        return puzzle;
    }

    // Get all puzzles
    public List<ShowPuzzle> getAllPuzzles() {
        // First collect the stream to a list
        List<ShowPuzzle> puzzles = puzzleRepository.findAll()
                .stream()
                .map(this::toDTO2)
                .collect(Collectors.toList());

        // Then reverse the list
        Collections.reverse(puzzles);

        return puzzles;
    }


    // Get puzzle by ID
    public Optional<PuzzleDTO> getPuzzleById(int id) {
        Optional<Puzzle> puzzle = puzzleRepository.findById(id);
        return puzzle.map(this::toDTO);
    }

    // Method to check if a solution is correct, with user authentication and puzzle completion logic
    public ResponseEntity<?> checkPuzzleSolution(int puzzleId, String providedSolution) {
        // Check if the user is logged in
        if (contextHolderService.getCurrentUser() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You must be logged in to check the solution.");
        }

        // Find the puzzle by ID
        Puzzle puzzle = puzzleRepository.findById(puzzleId)
                .orElseThrow(() -> new RuntimeException("Puzzle not found"));

        // Check if the provided solution matches the puzzle's solution
        if (puzzle.getSolution().equals(providedSolution)) {
            // Call the service to finish the current puzzle and start the next one
            return startPuzzleService.finishAndStartNewPuzzle(puzzle);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect solution.");
        }
    }
    // Update Puzzle
    public Optional<PuzzleDTO> updatePuzzle(int id, PuzzleDTO puzzleDTO) {
        Optional<Puzzle> existingPuzzle = puzzleRepository.findById(id);
        if (existingPuzzle.isPresent()) {
            Puzzle puzzle = existingPuzzle.get();
            puzzle.setPname(puzzleDTO.getPname());
            puzzle.setSolution(puzzleDTO.getSolution());
            return Optional.of(toDTO(puzzleRepository.save(puzzle)));
        } else {
            return Optional.empty();
        }
    }

    // Delete Puzzle
    public boolean deletePuzzle(int id) {
        if (puzzleRepository.existsById(id)) {
            puzzleRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
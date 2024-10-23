package com.mihai.Java_2024.features.puzzleFeature.service;

import com.mihai.Java_2024.config.ContextHolderService;
import com.mihai.Java_2024.features.puzzleFeature.dto.StartPuzzleDto;
import com.mihai.Java_2024.features.puzzleFeature.entity.Puzzle;
import com.mihai.Java_2024.features.puzzleFeature.entity.StartPuzzle;
import com.mihai.Java_2024.features.puzzleFeature.repository.PuzzleRepository;
import com.mihai.Java_2024.features.puzzleFeature.repository.StartPuzzleRepository;
import com.mihai.Java_2024.features.userFeature.entity.User;
import com.mihai.Java_2024.utils.Role;
import com.mihai.Java_2024.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkGlobalTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StartPuzzleService {

    @Autowired
    private StartPuzzleRepository startPuzzleRepository;

    @Autowired
    private PuzzleRepository puzzleRepository;

    @Autowired
    private ContextHolderService contextHolderService;

    // Convert DTO to Entity
    public StartPuzzle toEntity(StartPuzzleDto dto, Puzzle puzzle) {
        StartPuzzle startPuzzle = new StartPuzzle();
        startPuzzle.setStartTime(LocalDateTime.now());
        startPuzzle.setPuzzle(puzzle);
        return startPuzzle;
    }
    // Main logic to create StartPuzzle
    public ResponseEntity<?> createStartPuzzle(StartPuzzleDto dto) {
        // Check user role
        if (!contextHolderService.getCurrentUser().getRole().equals(Role.NORMAL_USER)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action with no permissions");
        }

        // Find the puzzle
        Puzzle puzzle = puzzleRepository.findById(dto.getPuzzleId())
                .orElseThrow(() -> new RuntimeException("Puzzle not found"));

        User currentUser = contextHolderService.getCurrentUser();

        // Check if there's an unfinished StartPuzzle (no finish time) for the user and puzzle
        Optional<StartPuzzle> existingStartPuzzle = startPuzzleRepository.findByUserAndPuzzle(currentUser, puzzle);

        if (existingStartPuzzle.isPresent() && existingStartPuzzle.get().getFinishTime() == null) {
            // If an existing puzzle is found and has no finish time set, return conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You already have an unfinished puzzle.");
        }
        if (existingStartPuzzle.isPresent() && existingStartPuzzle.get().getFinishTime() != null) {
            // If an existing puzzle is found and has no finish time set, return conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Puzzle finished but no more puzzles available.");
        }


        // Create new StartPuzzle if no unfinished one exists
        StartPuzzle startPuzzle = this.toEntity(dto, puzzle);
        startPuzzle.setUser(currentUser);

        StartPuzzle savedPuzzle = startPuzzleRepository.save(startPuzzle);

        if (savedPuzzle == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error creating puzzle start");
        }

        return ResponseEntity.ok(savedPuzzle);
    }

    public List<String> getUserEmailsAndCurrentLevel() {
        List<Object[]> results = startPuzzleRepository.findUsersWithCurrentPuzzle();

        // Use LinkedHashSet to maintain order and filter out duplicates
        Set<String> uniqueResults = new LinkedHashSet<>(
                results.stream()
                        .map(result -> "Email: " + result[0] + ", Current Level: " + result[1])
                        .collect(Collectors.toList())
        );

        // Convert the set back to a list
        return uniqueResults.stream().toList();
    }
    // Find started puzzle for the user and update finish_time
    public ResponseEntity<?> finishAndStartNewPuzzle(Puzzle solvedPuzzle) {
        User currentUser = contextHolderService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You must be logged in.");
        }

        // Find the started puzzle for the user that is not finished
        Optional<StartPuzzle> startedPuzzleOpt = startPuzzleRepository.findByUserAndPuzzleAndFinishTimeIsNull(currentUser, solvedPuzzle);

        if (startedPuzzleOpt.isPresent()) {
            StartPuzzle startedPuzzle = startedPuzzleOpt.get();
            // Update finish_time with the current timestamp
            startedPuzzle.setFinishTime(LocalDateTime.now());
            startPuzzleRepository.save(startedPuzzle);

            // Fetch the next puzzle (you can implement logic for fetching the next puzzle)
            Optional<Puzzle> nextPuzzleOpt = puzzleRepository.findNextPuzzle(solvedPuzzle.getId()); // This assumes you have logic for next puzzle

            if (nextPuzzleOpt.isPresent()) {
                Puzzle nextPuzzle = nextPuzzleOpt.get();

                // Start a new puzzle for the user
                StartPuzzle newStartPuzzle = new StartPuzzle();
                newStartPuzzle.setStartTime(LocalDateTime.now());
                newStartPuzzle.setPuzzle(nextPuzzle);
                newStartPuzzle.setUser(currentUser);
                startPuzzleRepository.save(newStartPuzzle);

                return ResponseEntity.ok("Puzzle finished and new puzzle started.");
            } else {
                return ResponseEntity.ok("Puzzle finished but no more puzzles available.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No started puzzle found for this user.");
        }
    }
    public ResponseEntity<?> getCurrentLevel(){
        User user = contextHolderService.getCurrentUser();
        if(user.getRole() == Role.NORMAL_USER){
            StartPuzzle startPuzzle = startPuzzleRepository.findByUserAndFinishTimeIsNull(user).get();
            if(startPuzzle!=null){
                return ResponseEntity.ok(startPuzzle.getPuzzle().getId());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("?????");
    }
}
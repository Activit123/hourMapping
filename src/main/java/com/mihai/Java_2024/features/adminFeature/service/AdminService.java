package com.mihai.Java_2024.features.adminFeature.service;

import com.mihai.Java_2024.config.ContextHolderService;
import com.mihai.Java_2024.features.adminFeature.dto.UserDTO;
import com.mihai.Java_2024.features.puzzleFeature.entity.StartPuzzle;
import com.mihai.Java_2024.features.userFeature.entity.User;
import com.mihai.Java_2024.features.userFeature.repository.UserRepository;
import com.mihai.Java_2024.features.puzzleFeature.repository.StartPuzzleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final StartPuzzleRepository startPuzzleRepository;
    private final ContextHolderService contextHolderService;

    public List<UserDTO> getAllPlayers() {

        User currentUser = contextHolderService.getCurrentUser();


        List<User> allUsers = userRepository.findAll();


        List<User> filteredUsers = allUsers.stream()
                .filter(user -> !user.getId().equals(currentUser.getId()))
                .toList();

        return filteredUsers.stream()
                .map(user -> {
                    // Obține toate puzzle-urile începute de utilizator
                    List<StartPuzzle> puzzles = startPuzzleRepository.findAllByUser(user);

                    // Calculăm timpul total petrecut pe puzzle-uri finalizate
                    Long totalTimeSpentMillis = puzzles.stream()
                            .filter(puzzle -> puzzle.getFinishTime() != null) // Filtrăm puzzle-urile terminate
                            .mapToLong(puzzle -> Duration.between(puzzle.getStartTime(), puzzle.getFinishTime()).toMillis())
                            .sum();

                    // Calculăm timpul petrecut pe puzzle-ul curent (dacă există)
                    StartPuzzle currentPuzzle = puzzles.stream()
                            .filter(puzzle -> puzzle.getFinishTime() == null) // Căutăm puzzle-ul care nu este finalizat
                            .findFirst()
                            .orElse(null); // Dacă nu există puzzle activ

                    if (currentPuzzle != null) {
                        long currentTimeSpentMillis = Duration.between(currentPuzzle.getStartTime(), LocalDateTime.now()).toMillis();
                        totalTimeSpentMillis += currentTimeSpentMillis; // Adăugăm timpul pe puzzle-ul curent
                    }

                    // Convertim timpul total din milisecunde în ore
                    double totalTimeSpentHours = totalTimeSpentMillis / (1000.0 * 60 * 60);

                    // Determinăm ID-ul puzzle-ului curent
                    Integer currentPuzzleId = currentPuzzle != null ? currentPuzzle.getId() : null;

                    // Returnăm DTO cu timpul total (în ore) și ID-ul puzzle-ului curent
                    return new UserDTO(user.getId(), user.getUsername(), totalTimeSpentHours, currentPuzzleId);
                })
                .collect(Collectors.toList());
    }
}


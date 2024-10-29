package com.mihai.Java_2024.features.adminFeature.service;

import com.mihai.Java_2024.config.ContextHolderService;
import com.mihai.Java_2024.features.adminFeature.dto.UserDTO;
import com.mihai.Java_2024.features.adminFeature.dto.UserLevelDTO;
import com.mihai.Java_2024.features.puzzleFeature.entity.StartPuzzle;
import com.mihai.Java_2024.features.userFeature.entity.User;
import com.mihai.Java_2024.features.userFeature.repository.UserRepository;
import com.mihai.Java_2024.features.puzzleFeature.repository.StartPuzzleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final StartPuzzleRepository startPuzzleRepository;
    private final ContextHolderService contextHolderService;
    public List<UserLevelDTO> getPlayerLevels(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<StartPuzzle> completedPuzzles = startPuzzleRepository.findAllByUser(user).stream()
                .filter(puzzle -> puzzle.getFinishTime() != null) // Filtrăm doar puzzle-urile terminate
                .collect(Collectors.toList());

        return completedPuzzles.stream()
                .map(puzzle -> new UserLevelDTO(puzzle.getPuzzle().getId(), puzzle.getFinishTime().toString()))
                .collect(Collectors.toList());
    }
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


                    // Calculăm timpul petrecut pe puzzle-ul curent (dacă există)
                    StartPuzzle lastPuzzle = puzzles.stream()
                            .filter(puzzle -> puzzle.getFinishTime() != null) // Căutăm puzzle-ul care nu este finalizat
                            .findFirst()
                            .orElse(null); // Dacă nu există puzzle activ
                    StartPuzzle currentPuzzle = puzzles.stream()
                            .filter(puzzle -> puzzle.getFinishTime() == null) // Căutăm puzzle-ul care nu este finalizat
                            .findFirst()
                            .orElse(null); // Dacă nu există puzzle activ


                    // Determinăm ID-ul puzzle-ului curent
                    Integer currentPuzzleId = currentPuzzle != null ? currentPuzzle.getPuzzle().getId() : 0;

                    if(lastPuzzle==null)
                        return new UserDTO(user.getId(), user.getUsername(),"0", currentPuzzleId);
                    else
                       return new UserDTO(user.getId(), user.getUsername(),lastPuzzle.getFinishTime().toString(), currentPuzzleId);
                })
                .collect(Collectors.toList());
    }
}


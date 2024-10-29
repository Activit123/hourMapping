package com.mihai.Java_2024.features.adminFeature.controller;

import com.mihai.Java_2024.features.adminFeature.dto.UserDTO;
import com.mihai.Java_2024.features.adminFeature.dto.UserLevelDTO;
import com.mihai.Java_2024.features.adminFeature.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/players")
    public ResponseEntity<List<UserDTO>> getAllPlayers() {
        List<UserDTO> players = adminService.getAllPlayers();
        return ResponseEntity.ok(players);
    }
    @GetMapping("/player-levels/{userId}")
    public ResponseEntity<List<UserLevelDTO>> getPlayerLevels(@PathVariable Integer userId) {
        List<UserLevelDTO> levels = adminService.getPlayerLevels(userId);
        return ResponseEntity.ok(levels);
    }
}

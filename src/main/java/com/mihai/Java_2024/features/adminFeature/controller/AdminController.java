package com.mihai.Java_2024.features.adminFeature.controller;

import com.mihai.Java_2024.features.adminFeature.dto.UserDTO;
import com.mihai.Java_2024.features.adminFeature.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}

package com.mihai.Java_2024.features.adminFeature.controller;

import com.mihai.Java_2024.features.adminFeature.dto.AuctionProposal;
import com.mihai.Java_2024.features.adminFeature.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;



}

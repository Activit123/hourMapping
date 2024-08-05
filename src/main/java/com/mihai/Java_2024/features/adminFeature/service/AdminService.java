package com.mihai.Java_2024.features.adminFeature.service;

import com.mihai.Java_2024.config.ContextHolderService;

import com.mihai.Java_2024.features.adminFeature.dto.AuctionProposal;


import com.mihai.Java_2024.features.userFeature.entity.User;
import com.mihai.Java_2024.utils.AuctionStatus;
import com.mihai.Java_2024.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AdminService {
    private final ContextHolderService contextHolderService;



    private boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }


}
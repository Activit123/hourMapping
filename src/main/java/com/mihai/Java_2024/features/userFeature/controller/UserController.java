package com.mihai.Java_2024.features.userFeature.controller;

import com.mihai.Java_2024.features.userFeature.dto.UserWithCompanyDto;
import com.mihai.Java_2024.features.userFeature.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserWithCompanyDto juridicUserDto) {
        return  userService.createUser(juridicUserDto);
    }

    @GetMapping
    public ResponseEntity<?> getUser(){
        return userService.getUser();
    }

    @PutMapping("/editUser")
    public ResponseEntity<?> editUser(@RequestBody UserWithCompanyDto userWithCompanyDto) throws IOException {
        return userService.editUser(userWithCompanyDto);
    }



}
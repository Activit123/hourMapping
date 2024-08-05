package com.mihai.Java_2024.features.authFeature.controller;

import com.mihai.Java_2024.features.authFeature.dto.AuthenticationRequest;
import com.mihai.Java_2024.features.authFeature.dto.AuthenticationResponse;
import com.mihai.Java_2024.features.authFeature.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping()
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
    {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}

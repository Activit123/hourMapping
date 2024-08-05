package com.mihai.Java_2024.features.authFeature.service;

import com.mihai.Java_2024.features.authFeature.dto.AuthenticationRequest;
import com.mihai.Java_2024.features.authFeature.dto.AuthenticationResponse;
import com.mihai.Java_2024.config.JwtService;
import com.mihai.Java_2024.features.userFeature.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword(),
                        userRepository.findByEmail(request.getEmail()).get().getRole().getAuthorities()
                )
        );

       var user =  userRepository.findByEmail(request.getEmail())
                .orElseThrow();
       var jwtToken = jwtService.generateToken(user);

       return AuthenticationResponse.builder()
               .accessToken(jwtToken)
               .build();
    }

}

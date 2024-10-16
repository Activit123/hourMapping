package com.mihai.Java_2024.features.userFeature.service;

import com.mihai.Java_2024.features.resetEmailFeature.dto.PasswordDto;
import com.mihai.Java_2024.features.resetEmailFeature.entity.RecoveryToken;
import com.mihai.Java_2024.features.resetEmailFeature.repository.RecoveryTokenRepository;
import com.mihai.Java_2024.features.resetEmailFeature.service.RecoveryTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class UserSecurityService {
    private final UserService userService;
    private final RecoveryTokenService recoveryTokenService;
    private final RecoveryTokenRepository recoveryTokenRepository;

    public boolean validatePasswordRecoveryToken(String token) {
        final RecoveryToken recoveryToken = recoveryTokenRepository.findByToken(token);

        return isTokenFound(recoveryToken) && isTokenExpired(recoveryToken);
    }

    private boolean isTokenFound(RecoveryToken token){
        return token != null;
    }

    private boolean isTokenExpired(RecoveryToken token){
        Long currentTime = LocalDate.now().toEpochDay();
        return (currentTime - token.getExpiration()) <= 0;
    }

    public ResponseEntity<String> changePassword(PasswordDto passwordDto) {
        if (passwordDto != null) {
            userService.changeUserPassword(passwordDto);
            recoveryTokenService.deleteToken(passwordDto.getToken());
            return new ResponseEntity<>("Password reset", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
}


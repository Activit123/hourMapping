package com.mihai.Java_2024.features.resetEmailFeature.service;


import com.mihai.Java_2024.features.resetEmailFeature.entity.RecoveryToken;
import com.mihai.Java_2024.features.resetEmailFeature.repository.RecoveryTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecoveryTokenService {
    private final RecoveryTokenRepository recoveryTokenRepository;

    public RecoveryToken getByToken(String token) {
        return recoveryTokenRepository.findByToken(token);
    }
    public void deleteToken(String token) {
        recoveryTokenRepository.delete(getByToken(token));
    }
}

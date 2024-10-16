package com.mihai.Java_2024.features.userFeature.service;

import com.mihai.Java_2024.exceptions.UserAlreadyExistsException;
import com.mihai.Java_2024.exceptions.UserNotFoundException;
import com.mihai.Java_2024.features.resetEmailFeature.dto.PasswordDto;
import com.mihai.Java_2024.features.resetEmailFeature.entity.RecoveryToken;
import com.mihai.Java_2024.features.resetEmailFeature.repository.RecoveryTokenRepository;
import com.mihai.Java_2024.features.userFeature.dto.UserEmailDto;
import com.mihai.Java_2024.features.userFeature.dto.UserWithCompanyDto;
import com.mihai.Java_2024.features.userFeature.entity.User;

import com.mihai.Java_2024.config.ContextHolderService;
import com.mihai.Java_2024.features.userFeature.repository.UserRepository;

import com.mihai.Java_2024.s3config.S3Service;
import com.mihai.Java_2024.utils.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecoveryTokenRepository recoveryTokenRepository;
    private final S3Service s3Service;
    private final ContextHolderService contextHolderService;
    public ResponseEntity<?> createUser(UserWithCompanyDto juridicUserDto) {

        if (userRepository.findByEmail(juridicUserDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }


        User user = new User();
        user.setFirstName(juridicUserDto.getFirstName());
        user.setLastName(juridicUserDto.getLastName());
        user.setEmail(juridicUserDto.getEmail());
        user.setRole(Role.NORMAL_USER);
        user.setPassword(passwordEncoder.encode(juridicUserDto.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Account created");
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public ResponseEntity<?> getUser(){
        User currentUser = contextHolderService.getCurrentUser();
        String userEmail = null;

        if (currentUser != null) {
            userEmail = currentUser.getEmail();
        }
        if(userEmail == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user is not found!");
        }
        User user = userRepository.findByEmail(userEmail).get();
        UserWithCompanyDto userInfo = new UserWithCompanyDto();
        userInfo.setId(user.getId());
        userInfo.setFirstName(user.getFirstName());
        userInfo.setLastName(user.getLastName());
        userInfo.setEmail(user.getEmail());
        return ResponseEntity.ok(userInfo);
    }

    public ResponseEntity<?> editUser(UserWithCompanyDto userWithCompanyDto) throws IOException {

        User currentUser = contextHolderService.getCurrentUser();
        String userEmail = null;

        if (currentUser != null) {
            userEmail = currentUser.getEmail();
        }

        User user = userRepository.findByEmail(userEmail).get();
        user.setFirstName(userWithCompanyDto.getFirstName());
        user.setLastName(userWithCompanyDto.getLastName());
        user.setEmail(userWithCompanyDto.getEmail());
        user.setRole(userWithCompanyDto.getRole());
        user.setPassword(passwordEncoder.encode(userWithCompanyDto.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User updated");
    }

    public UserEmailDto getUserByEmail(String email) throws UserNotFoundException {
        UserEmailDto userEmailDto = convertToDto(userRepository.findUserByEmail(email));
        if (userEmailDto == null) {
            throw new UserNotFoundException("User not found");
        }
        return convertToDto(userRepository.findUserByEmail(email));
    }

    public RecoveryToken createPasswordRecoveryTokenForUser(UserEmailDto userEmailDto, final String token, Instant expiration) throws UserNotFoundException {
        if (userEmailDto == null) {
            throw new UserNotFoundException("User not found");
        }
        final RecoveryToken recoveryToken = new RecoveryToken(userEmailDto.getEmail(), token, expiration.getEpochSecond(), convertToEntity(userEmailDto));
        log.info(recoveryToken.toString());
        recoveryTokenRepository.save(recoveryToken);
        return recoveryToken;
    }

    public void changeUserPassword(PasswordDto passwordDto) {
        RecoveryToken recoveryToken = recoveryTokenRepository.findByToken(passwordDto.getToken());
        User user = userRepository.findUserByEmail(recoveryToken.getUserEmail());
        user.setPassword(new BCryptPasswordEncoder().encode((passwordDto.getNewPassword())));
        userRepository.save(user);
    }


    private UserEmailDto convertToDto(User user) throws UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        UserEmailDto userEmailDto = new UserEmailDto();
        userEmailDto.setId(user.getId());
        userEmailDto.setEmail(user.getEmail());
        return userEmailDto;
    }

    private User convertToEntity(UserEmailDto userEmailDto) {
        return userRepository.findUserByEmail(userEmailDto.getEmail());
    }

    public String constructPasswordResetEmailBody(String origin, String token){
        String body = "Here is your password recovery code: \n\n";
        String url = token + "\n\n Now go back to the application and paste it in the required field!";
        return body + url;
    }

    public RecoveryToken generateRecoveryToken(UserEmailDto userEmailDto) throws UserNotFoundException {
        String token = UUID.randomUUID().toString();

        log.info(token);

        Instant currentTime = Instant.now();
        long SECONDS_IN_A_DAY = 86400L;
        return createPasswordRecoveryTokenForUser(userEmailDto, token, currentTime.plusSeconds(SECONDS_IN_A_DAY));
    }

    public ResponseEntity<String> redirect() {
        //redirect
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "recover/auth/resetPassword");
        return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
    }
}
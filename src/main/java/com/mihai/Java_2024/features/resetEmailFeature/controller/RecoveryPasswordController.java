package com.mihai.Java_2024.features.resetEmailFeature.controller;


import com.mihai.Java_2024.exceptions.UserNotFoundException;
import com.mihai.Java_2024.features.resetEmailFeature.dto.PasswordDto;
import com.mihai.Java_2024.features.resetEmailFeature.email.Email;
import com.mihai.Java_2024.features.resetEmailFeature.email.service.EmailService;
import com.mihai.Java_2024.features.resetEmailFeature.entity.RecoveryToken;
import com.mihai.Java_2024.features.resetEmailFeature.service.RecoveryTokenService;
import com.mihai.Java_2024.features.userFeature.dto.UserEmailDto;
import com.mihai.Java_2024.features.userFeature.service.UserSecurityService;
import com.mihai.Java_2024.features.userFeature.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class RecoveryPasswordController {
    String FRONTEND_DOMAIN = "http://internship2024-frontend-green.dev.assist.ro";
    private final UserService userService;
    private final UserSecurityService userSecurityService;
    private final EmailService emailService;
    private final RecoveryTokenService recoveryTokenService;

    @PostMapping("/forgot")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String userEmail) throws UserNotFoundException {

        UserEmailDto userEmailDto = userService.getUserByEmail(userEmail);
        RecoveryToken recoveryToken = userService.generateRecoveryToken(userEmailDto);

        emailService.sendSimpleEmail(new Email(userEmail, "Reset Password", userService.constructPasswordResetEmailBody(FRONTEND_DOMAIN, recoveryToken.getToken())));

        return ResponseEntity.status(HttpStatus.OK).body("User logged in successfully");
    }

    @GetMapping("/recover")
    public ResponseEntity<String> recoverPassword(@RequestParam("token") String token) throws UserNotFoundException {

        RecoveryToken recoveryToken = recoveryTokenService.getByToken(token);

        String userEmail = recoveryToken.getUserEmail();

        if (!userSecurityService.validatePasswordRecoveryToken(token)) {
            //expired or not found
            return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
        }

        UserEmailDto userEmailDto = userService.getUserByEmail(userEmail);

        return userService.redirect();
    }

    @PostMapping("/recover/auth/resetPassword")
    public ResponseEntity<String> savePassword(@RequestBody PasswordDto passwordDto) throws UserNotFoundException {
        log.info(passwordDto.toString());
        boolean result = userSecurityService.validatePasswordRecoveryToken(passwordDto.getToken());

        if (!result) {
            return new ResponseEntity<>("Token expired", HttpStatus.UNAUTHORIZED);
        }

        return userSecurityService.changePassword(passwordDto);
    }
}

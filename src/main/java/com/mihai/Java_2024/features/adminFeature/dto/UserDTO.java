package com.mihai.Java_2024.features.adminFeature.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String email;
    private String totalTimeSpent; // Adăugat pentru timpul total petrecut
    private Integer currentLevel; // Numele puzzle-ului/nivelului curent
}

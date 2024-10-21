package com.mihai.Java_2024.features.adminFeature.dto;

import lombok.*;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String email;
    private Double totalTimeSpent; // Adăugat pentru timpul total petrecut
    private Integer currentLevel; // Numele puzzle-ului/nivelului curent
}

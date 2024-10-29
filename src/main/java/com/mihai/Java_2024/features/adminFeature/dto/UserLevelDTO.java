package com.mihai.Java_2024.features.adminFeature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLevelDTO {
    private Integer levelId;
    private String finishTime;
}

package com.mihai.Java_2024.features.statisticsFeature.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class CategoryBalanceDTO {
    private String categoryName;
    private double estimatedBalance;
    public CategoryBalanceDTO(String categoryName, double estimatedBalance) {
        this.categoryName = categoryName;
        this.estimatedBalance = estimatedBalance;
    }
}

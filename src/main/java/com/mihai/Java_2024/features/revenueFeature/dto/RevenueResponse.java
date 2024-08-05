package com.mihai.Java_2024.features.revenueFeature.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class RevenueResponse {
    private Integer id;
    private String hoursWorked;
    private LocalDateTime currDay;
    private String currency;
    private String categoryName;
}

package com.mihai.Java_2024.features.revenueFeature.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class RevenueRequest {

    private String hoursWorked;
    private LocalDateTime currDay;
    private String currency;
    private Integer category_id;
}

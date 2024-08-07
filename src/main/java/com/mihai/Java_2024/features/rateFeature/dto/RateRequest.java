package com.mihai.Java_2024.features.rateFeature.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateRequest {
    private double rate;
    private int categoryId;
}
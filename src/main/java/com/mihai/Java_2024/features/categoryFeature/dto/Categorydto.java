package com.mihai.Java_2024.features.categoryFeature.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Categorydto {
    @NotNull
    private String categoryName;
}

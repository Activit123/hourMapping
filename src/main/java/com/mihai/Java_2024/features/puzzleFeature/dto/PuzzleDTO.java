package com.mihai.Java_2024.features.puzzleFeature.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PuzzleDTO {
    private String pname;
    @JsonIgnore
    private String solution;
}
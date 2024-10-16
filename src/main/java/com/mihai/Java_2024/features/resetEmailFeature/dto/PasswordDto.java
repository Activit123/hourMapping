package com.mihai.Java_2024.features.resetEmailFeature.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class PasswordDto {
    @JsonIgnore
    private String oldPassword;
    private String token;
    private String newPassword;

}

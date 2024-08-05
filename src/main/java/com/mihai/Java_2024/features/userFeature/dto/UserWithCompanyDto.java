package com.mihai.Java_2024.features.userFeature.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mihai.Java_2024.utils.Role;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserWithCompanyDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private Role role;
    private String password;


}
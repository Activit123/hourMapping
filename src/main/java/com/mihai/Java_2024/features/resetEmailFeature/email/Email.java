package com.mihai.Java_2024.features.resetEmailFeature.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {
    private String recipient;
    private String subject;
    private String message;
}

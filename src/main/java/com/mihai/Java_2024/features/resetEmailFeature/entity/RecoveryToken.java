package com.mihai.Java_2024.features.resetEmailFeature.entity;

import com.mihai.Java_2024.features.userFeature.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "recovery_tokens")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecoveryToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userEmail;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Long expiration; //timestamp

    public RecoveryToken(String email, String token, long epochSecond, User user) {
        this.token = token;
        this.userEmail = email;
        this.user = user;
        this.expiration = epochSecond;
    }
}

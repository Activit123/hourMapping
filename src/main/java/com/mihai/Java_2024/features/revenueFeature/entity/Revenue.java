package com.mihai.Java_2024.features.revenueFeature.entity;

import com.mihai.Java_2024.features.categoryFeature.entity.Category;
import com.mihai.Java_2024.features.userFeature.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "revenue")
@Getter
@Setter
public class Revenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "hours_worked", nullable = false)
    private String hoursWorked;

    @Column(name = "curr_day", nullable = false)
    private LocalDateTime currDay;

    @Column(name = "currency", nullable = false)
    private String currency;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    }
package com.mihai.Java_2024.features.categoryFeature.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mihai.Java_2024.features.userFeature.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

}
package com.mihai.Java_2024.features.rateFeature.entity;

import com.mihai.Java_2024.features.categoryFeature.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "rates")
@Getter
@Setter
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
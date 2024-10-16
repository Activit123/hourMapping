package com.mihai.Java_2024.features.puzzleFeature.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "puzzle")
@Getter
@Setter
public class Puzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pname", nullable = false)
    private String pname;

    @Column(name = "solution", nullable = false)
    private String solution;
}

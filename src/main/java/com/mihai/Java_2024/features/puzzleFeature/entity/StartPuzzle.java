package com.mihai.Java_2024.features.puzzleFeature.entity;

import com.mihai.Java_2024.features.userFeature.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "start_puzzles")
@Getter
@Setter
public class StartPuzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "puzzle_id", nullable = false)
    private Puzzle puzzle;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "finish_time")
    private LocalDateTime finishTime;

    // Initialize startTime in Bucharest timezone
    @PrePersist
    public void prePersist() {
        if (this.startTime == null) {
            this.startTime = ZonedDateTime.now(ZoneId.of("Europe/Bucharest")).toLocalDateTime();
        }
    }

    // Set finishTime in Bucharest timezone when puzzle finishes
    public void completePuzzle() {
        this.finishTime = ZonedDateTime.now(ZoneId.of("Europe/Bucharest")).toLocalDateTime();
    }
}

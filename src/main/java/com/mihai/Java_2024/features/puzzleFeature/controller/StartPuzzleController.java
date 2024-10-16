package com.mihai.Java_2024.features.puzzleFeature.controller;

import com.mihai.Java_2024.features.puzzleFeature.dto.StartPuzzleDto;
import com.mihai.Java_2024.features.puzzleFeature.service.StartPuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/start-puzzles")
public class StartPuzzleController {

    @Autowired
    private StartPuzzleService startPuzzleService;

    @PostMapping
    public ResponseEntity<?> createStartPuzzle(@RequestBody StartPuzzleDto dto) {
        return startPuzzleService.createStartPuzzle(dto);
    }
}
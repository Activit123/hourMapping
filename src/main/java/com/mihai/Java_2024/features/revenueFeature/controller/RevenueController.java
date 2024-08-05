package com.mihai.Java_2024.features.revenueFeature.controller;

import com.mihai.Java_2024.features.revenueFeature.dto.RevenueRequest;
import com.mihai.Java_2024.features.revenueFeature.entity.Revenue;
import com.mihai.Java_2024.features.revenueFeature.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revenues")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @GetMapping
    public ResponseEntity<?> getAllRevenues() {
        return revenueService.getAllRevenues();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revenue> getRevenueById(@PathVariable int id) {
        return revenueService.getRevenueById(id);
    }

    @PostMapping
    public ResponseEntity<?> createRevenue(@RequestBody RevenueRequest revenue) {
        return revenueService.createRevenue(revenue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Revenue> updateRevenue(@PathVariable int id, @RequestBody Revenue revenueDetails) {
        return revenueService.updateRevenue(id, revenueDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevenue(@PathVariable int id) {
        return revenueService.deleteRevenue(id);
    }
}
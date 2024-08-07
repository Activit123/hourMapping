package com.mihai.Java_2024.features.rateFeature.controller;

import com.mihai.Java_2024.features.rateFeature.dto.RateRequest;
import com.mihai.Java_2024.features.rateFeature.entity.Rate;
import com.mihai.Java_2024.features.rateFeature.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rates")
public class RateController {

    @Autowired
    private RateService rateService;

    @GetMapping
    public List<Rate> getAllRates() {
        return rateService.getAllRates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rate> getRateById(@PathVariable int id) {
        Optional<Rate> rate = rateService.getRateByCategoryId(id);
        return rate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Rate createRate(@RequestBody RateRequest rateRequest) {
        return rateService.createRate(rateRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRate(@PathVariable int id, @RequestBody RateRequest rateRequest) {
        Rate updatedRate = rateService.updateRate(id, rateRequest);
        return ResponseEntity.ok(updatedRate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable int id) {
        rateService.deleteRate(id);
        return ResponseEntity.noContent().build();
    }
}
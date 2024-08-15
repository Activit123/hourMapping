package com.mihai.Java_2024.features.statisticsFeature.controller;

import com.mihai.Java_2024.features.statisticsFeature.dto.CategoryBalanceDTO;
import com.mihai.Java_2024.features.statisticsFeature.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/category-balances")
    public List<CategoryBalanceDTO> getCategoryBalances() {
        return statisticsService.getEstimatedBalanceByCategory();
    }
}
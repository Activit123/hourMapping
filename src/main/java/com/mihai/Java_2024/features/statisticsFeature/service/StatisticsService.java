package com.mihai.Java_2024.features.statisticsFeature.service;

import com.mihai.Java_2024.config.ContextHolderService;
import com.mihai.Java_2024.features.statisticsFeature.dto.CategoryBalanceDTO;
import com.mihai.Java_2024.features.statisticsFeature.repository.StatisticsRepository;
import com.mihai.Java_2024.features.userFeature.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private ContextHolderService contextHolderService;

    public List<CategoryBalanceDTO> getEstimatedBalanceByCategory() {
        User user = contextHolderService.getCurrentUser();
        List<Object[]> rawData = statisticsRepository.findRawDataForCategoryBalance(user.getId());

        Map<String, Double> balanceMap = new HashMap<>();
        Map<String, Double> hourMap = new HashMap<>();

        for (Object[] row : rawData) {
            String categoryName = (String) row[0];
            double hoursWorked = (double) row[1];
            Double rate = (Double) row[2];

            if (rate != null) {
                double estimatedBalance = hoursWorked * rate;
                balanceMap.merge(categoryName, estimatedBalance, Double::sum);
            }

            hourMap.merge(categoryName, hoursWorked, Double::sum);
        }

        return hourMap.entrySet().stream()
                .map(entry -> new CategoryBalanceDTO(entry.getKey(), balanceMap.getOrDefault(entry.getKey(), 0.0), entry.getValue()))
                .toList();
    }
}
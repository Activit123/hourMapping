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
        // Fetch raw data from the repository
        User user = contextHolderService.getCurrentUser();
        List<Object[]> rawData = statisticsRepository.findRawDataForCategoryBalance(user.getId());

        Map<String, Double> balanceMap = new HashMap<>();

        // Iterate over the raw data and calculate the estimated balance
        for (Object[] row : rawData) {
            String categoryName = (String) row[0];
            String hoursWorkedStr = (String) row[1];
            Double rate = (Double) row[2]; // Can be null

            // Skip categories without a rate
            if (rate == null) {
                continue;
            }

            // Convert hoursWorked from String to double
            double hoursWorked = Double.parseDouble(hoursWorkedStr);

            // Calculate the estimated balance for this row
            double estimatedBalance = hoursWorked * rate;

            // Accumulate the balance per category
            balanceMap.merge(categoryName, estimatedBalance, Double::sum);
        }

        // Convert the map entries to DTOs
        return balanceMap.entrySet().stream()
                .map(entry -> new CategoryBalanceDTO(entry.getKey(), entry.getValue()))
                .toList();
    }
}
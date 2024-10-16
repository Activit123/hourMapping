package com.mihai.Java_2024.features.statisticsFeature.dto;

public class CategoryBalanceDTO {

    private String categoryName;
    private Double estimatedBalance;
    private Double totalHoursWorked; // New field

    public CategoryBalanceDTO(String categoryName, Double estimatedBalance, Double totalHoursWorked) {
        this.categoryName = categoryName;
        this.estimatedBalance = estimatedBalance;
        this.totalHoursWorked = totalHoursWorked;
    }

    // Getters and setters for the fields

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getEstimatedBalance() {
        return estimatedBalance;
    }

    public void setEstimatedBalance(Double estimatedBalance) {
        this.estimatedBalance = estimatedBalance;
    }

    public Double getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(Double totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }
}

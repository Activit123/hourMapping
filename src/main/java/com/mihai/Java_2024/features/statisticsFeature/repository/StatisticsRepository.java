package com.mihai.Java_2024.features.statisticsFeature.repository;

import com.mihai.Java_2024.features.categoryFeature.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends CrudRepository<Category, Integer> {

    @Query("SELECT c.categoryName, rv.hoursWorked, r.rate " +
            "FROM Category c " +
            "LEFT JOIN Rate r ON c.id = r.category.id " +
            "JOIN Revenue rv ON c.id = rv.category.id " +
            "WHERE rv.user.id = :userId")
    List<Object[]> findRawDataForCategoryBalance(@Param("userId") Integer userId);
}
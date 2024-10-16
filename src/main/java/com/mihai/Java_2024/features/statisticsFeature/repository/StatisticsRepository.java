package com.mihai.Java_2024.features.statisticsFeature.repository;

import com.mihai.Java_2024.features.categoryFeature.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends CrudRepository<Category, Integer> {

    @Query("SELECT c.categoryName, " +
            "COALESCE(SUM(CAST(rv.hoursWorked AS double)), 0) AS totalHours, " +
            "COALESCE(r.rate, 0) AS rate " +
            "FROM Category c " +
            "LEFT JOIN Revenue rv ON c.id = rv.category.id AND rv.user.id = :userId " +
            "LEFT JOIN Rate r ON c.id = r.category.id " +
            "WHERE rv.user.id = :userId " +
            "GROUP BY c.categoryName, r.rate")
    List<Object[]> findRawDataForCategoryBalance(@Param("userId") Integer userId);

}

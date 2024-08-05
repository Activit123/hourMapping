package com.mihai.Java_2024.features.categoryFeature.repository;

import com.mihai.Java_2024.features.categoryFeature.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
Optional<List<Category>> findCategoriesByUserId(Integer id);
Optional<List<Category>> findCategoriesByCategoryName(String categoryName);
}
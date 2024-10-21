package com.mihai.Java_2024.features.categoryFeature.service;

import com.mihai.Java_2024.config.ContextHolderService;
import com.mihai.Java_2024.config.JwtService;
import com.mihai.Java_2024.features.categoryFeature.dto.Categorydto;
import com.mihai.Java_2024.features.categoryFeature.entity.Category;
import com.mihai.Java_2024.features.categoryFeature.repository.CategoryRepository;
import com.mihai.Java_2024.features.rateFeature.repository.RateRepository;
import com.mihai.Java_2024.features.revenueFeature.entity.Revenue;
import com.mihai.Java_2024.features.revenueFeature.repository.RevenueRepository;
import com.mihai.Java_2024.utils.Role;
import lombok.RequiredArgsConstructor;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RevenueRepository revenueRepository;
    @Autowired
    private RateRepository rateRepository;
    private final ContextHolderService contextHolderService;
    public List<Category> getAllCategories() {

        Optional<List<Category>> categories = categoryRepository.findCategoriesByUserId(contextHolderService.getCurrentUser().getId());
        if(!categories.isPresent()){
            return null;
        }
        return categories.get();
    }

   /* public ResponseEntity<Category> getCategoryById(int id) {
        Optional<Category> category = categoryRepository.getById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> createCategory(Categorydto category) {
        Category category1 = new Category();
        if(!contextHolderService.getCurrentUser().getRole().equals(Role.NORMAL_USER)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action with no permissions");
        }
        category1.setUser(contextHolderService.getCurrentUser());
        category1.setCategoryName(category.getCategoryName());
        Category cat = categoryRepository.save(category1);
        if(cat==null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error");
        }
        return ResponseEntity.ok("Category created");
    }

    public ResponseEntity<?> updateCategory(int id, String categoryDetails) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category existingCategory = category.get();

            existingCategory.setCategoryName(categoryDetails);
            Category updatedCategory = categoryRepository.save(existingCategory);
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteCategory(int id) {
        Optional<Category> category = categoryRepository.getById(id);
        if (category.isPresent()) {
           Optional<List<Revenue>> revenues = revenueRepository.findRevenuesByCategoryId(id);
            if(revenues.isPresent()){
                for(Revenue r:revenues.get()){
                    r.setTitle(r.getCategory().getCategoryName());
                    r.setCategory(null);
                    revenueRepository.save(r);
                }
            }
            if(rateRepository.findByCategoryId(id).isPresent()){
                rateRepository.deleteById(rateRepository.findByCategoryId(id).get().getId());
            }
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
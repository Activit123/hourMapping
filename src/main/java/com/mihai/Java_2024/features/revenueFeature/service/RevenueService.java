package com.mihai.Java_2024.features.revenueFeature.service;

import com.mihai.Java_2024.config.ContextHolderService;
import com.mihai.Java_2024.features.categoryFeature.entity.Category;
import com.mihai.Java_2024.features.categoryFeature.repository.CategoryRepository;
import com.mihai.Java_2024.features.revenueFeature.dto.ChangeRevenue;
import com.mihai.Java_2024.features.revenueFeature.dto.RevenueRequest;
import com.mihai.Java_2024.features.revenueFeature.dto.RevenueResponse;
import com.mihai.Java_2024.features.revenueFeature.entity.Revenue;
import com.mihai.Java_2024.features.revenueFeature.repository.RevenueRepository;
import com.mihai.Java_2024.features.userFeature.entity.User;
import com.mihai.Java_2024.features.userFeature.repository.UserRepository;
import com.mihai.Java_2024.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ContextHolderService contextHolderService;
    public ResponseEntity<?> getAllRevenues() {
        List<Revenue> revenues = revenueRepository.findAll();
        List<RevenueResponse> revenueResponses = new ArrayList<>();
        User user = contextHolderService.getCurrentUser();
        for(Revenue r:revenues){
            if(r.getUser().getId()!=user.getId()){
                continue;
            }
            RevenueResponse revenueResponse = new RevenueResponse();
            if(r.getCategory() == null){
                revenueResponse.setCategoryName(r.getTitle());
            }else {
                revenueResponse.setCategoryName(r.getCategory().getCategoryName());
            }
            revenueResponse.setCurrency(r.getCurrency());
            revenueResponse.setHoursWorked(r.getHoursWorked());
            revenueResponse.setCurrDay(r.getCurrDay());
            revenueResponse.setId(r.getId());
            revenueResponses.add(revenueResponse);
        }
        if(revenueResponses.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No entries");
        }
        return ResponseEntity.ok(revenueResponses);
    }

    public ResponseEntity<Revenue> getRevenueById(int id) {
        Optional<Revenue> revenue = revenueRepository.findById(id);
        return revenue.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> createRevenue(RevenueRequest revenue) {
        Revenue revenue1 = new Revenue();
       // revenue1.setTitle("");
        revenue1.setHoursWorked(revenue.getHoursWorked());
        revenue1.setCurrDay(revenue.getCurrDay());
        revenue1.setCurrency(revenue.getCurrency());
        if(!contextHolderService.getCurrentUser().getRole().equals(Role.NORMAL_USER)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No allow");
        }
        contextHolderService.getCurrentUser();
        revenue1.setUser(contextHolderService.getCurrentUser());
    //    revenue1.setUser(userRepository.findById(revenue.getUser_id()).get());
        revenue1.setCategory(categoryRepository.findById(revenue.getCategory_id()).get());
        revenue1.setTitle(categoryRepository.findById(revenue.getCategory_id()).get().getCategoryName());
       Revenue saveRevenue =  revenueRepository.save(revenue1);
        return ResponseEntity.ok(saveRevenue);
    }

    public ResponseEntity<?> updateRevenue(int id, ChangeRevenue revenueDetails) {
        Optional<Revenue> revenue = revenueRepository.findById(id);
      //  return ResponseEntity.ok(revenue.get());
        if (revenue.isPresent()) {
            Revenue existingRevenue = revenue.get();
            Optional <List<Category>> categories = categoryRepository.findCategoriesByCategoryName(revenueDetails.getNewRevenueName());
          //  return ResponseEntity.ok(categories.get());
            if(!categories.get().isEmpty()){
                existingRevenue.setCategory(categories.get().getFirst());
            }else {
                existingRevenue.setTitle(revenueDetails.getNewRevenueName());
            }
            Revenue updatedRevenue = revenueRepository.save(existingRevenue);
            return ResponseEntity.ok(updatedRevenue);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not foun");
        }
    }

    public ResponseEntity<Void> deleteRevenue(int id) {
        Optional<Revenue> revenue = revenueRepository.findById(id);
        if (revenue.isPresent()) {
            revenueRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
package com.mihai.Java_2024.features.rateFeature.service;

import com.mihai.Java_2024.features.categoryFeature.entity.Category;
import com.mihai.Java_2024.features.categoryFeature.repository.CategoryRepository;
import com.mihai.Java_2024.features.rateFeature.dto.RateRequest;
import com.mihai.Java_2024.features.rateFeature.entity.Rate;
import com.mihai.Java_2024.features.rateFeature.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }

    public Optional<Rate> getRateById(int id) {

        return rateRepository.findById(id);
    }
    public Optional<Rate> getRateByCategoryId(int categoryId) {
        return rateRepository.findByCategoryId(categoryId);
    }

    public Rate createRate(RateRequest rateRequest) {
        Rate rate = new Rate();
        rate.setRate(rateRequest.getRate());
        Optional<Rate> existingRate = rateRepository.findByCategoryId(rateRequest.getCategoryId());
        if(existingRate.isPresent()){
            existingRate.get().setRate(rateRequest.getRate());
            return rateRepository.save(existingRate.get());
        }
        Optional<Category> category = categoryRepository.findById(rateRequest.getCategoryId());
        category.ifPresent(rate::setCategory);
        return rateRepository.save(rate);
    }

    public Rate updateRate(int id, RateRequest rateRequest) {
        Rate rate = rateRepository.findById(id).orElseThrow(() -> new RuntimeException("Rate not found with id " + id));
        rate.setRate(rateRequest.getRate());
        Optional<Category> category = categoryRepository.findById(rateRequest.getCategoryId());
        category.ifPresent(rate::setCategory);
        return rateRepository.save(rate);
    }

    public void deleteRate(int id) {
        Rate rate = rateRepository.findById(id).orElseThrow(() -> new RuntimeException("Rate not found with id " + id));
        rateRepository.delete(rate);
    }
}
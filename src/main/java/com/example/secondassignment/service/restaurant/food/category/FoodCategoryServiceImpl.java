package com.example.secondassignment.service.restaurant.food.category;

import com.example.secondassignment.model.Category;
import com.example.secondassignment.repository.FoodCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class FoodCategoryServiceImpl implements FoodCategoryService {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAll() {
        return foodCategoryRepository.findAll();
    }
}

package com.example.secondassignment.service.restaurant.food.category;

import com.example.secondassignment.model.Category;

import java.util.List;

/**
 * Interface for the service of a food category.
 */
public interface FoodCategoryService {

    /**
     * Retrieves all food categories from the database.
     * @return The list containing all existing food categories.
     */
    List<Category> findAll();
}

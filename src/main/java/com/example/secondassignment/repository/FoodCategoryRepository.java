package com.example.secondassignment.repository;

import com.example.secondassignment.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepository extends JpaRepository<Category, Integer> {
}

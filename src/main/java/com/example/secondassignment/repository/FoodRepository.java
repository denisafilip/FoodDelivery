package com.example.secondassignment.repository;

import com.example.secondassignment.model.Food;
import com.example.secondassignment.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    Optional<Food> findByNameAndRestaurant(String name, Restaurant restaurant);

    Optional<List<Food>> findByRestaurant(Restaurant restaurant);
}

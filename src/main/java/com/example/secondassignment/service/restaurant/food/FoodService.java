package com.example.secondassignment.service.restaurant.food;

import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.Food;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateFoodNameException;

import javax.transaction.Transactional;
import java.util.List;

public interface FoodService {
    @Transactional
    FoodDTO save(FoodDTO foodDTO) throws DuplicateFoodNameException, InvalidDataException;

    Food findByNameAndRestaurant(String name, Restaurant restaurant);

    List<FoodDTO> findByRestaurant(String restaurantName);
}

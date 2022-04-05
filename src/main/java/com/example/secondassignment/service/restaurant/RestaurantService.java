package com.example.secondassignment.service.restaurant;

import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;

import javax.transaction.Transactional;
import java.util.List;

public interface RestaurantService {

    @Transactional
    RestaurantDTO save(RestaurantDTO restaurantDTO) throws InvalidDataException, DuplicateRestaurantNameException;

    List<RestaurantDTO> findAll();

    String delete(Integer id);

    Restaurant findByName(String name);
}

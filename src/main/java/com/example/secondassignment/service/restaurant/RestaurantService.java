package com.example.secondassignment.service.restaurant;

import com.example.secondassignment.DTO.RestaurantDTO;
import com.example.secondassignment.model.Restaurant;

import javax.transaction.Transactional;
import java.util.List;

public interface RestaurantService {

    @Transactional
    RestaurantDTO save(Restaurant restaurant);

    List<RestaurantDTO> findAll();

    String delete(Integer id);
}

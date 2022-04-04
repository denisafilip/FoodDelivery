package com.example.secondassignment.controller;

import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.DTO.RestaurantDTO;
import com.example.secondassignment.mappers.AdministratorMapper;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.service.restaurant.RestaurantService;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @PostMapping("/save")
    public ResponseEntity<RestaurantDTO> save(@Validated @RequestBody Restaurant restaurant) {
        try {
            return new ResponseEntity<>(restaurantService.save(restaurant), HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

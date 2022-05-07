package com.example.secondassignment.controller;

import com.example.secondassignment.model.Category;
import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.service.address.zone.ZoneServiceImpl;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.food.category.FoodCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private ZoneServiceImpl zoneService;

    @Autowired
    private FoodCategoryServiceImpl foodCategoryService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String getAdministrators() {
        return "Hello there!";
    }

    @GetMapping("/getZones")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Zone>> getZones() {
        try {
            return new ResponseEntity<>(zoneService.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getFoodCategories")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Category>> getFoodCategories() {
        return new ResponseEntity<>(foodCategoryService.findAll(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getRestaurants")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<RestaurantDTO>> findRestaurants() {
        return new ResponseEntity<>(restaurantService.findAll(), HttpStatus.ACCEPTED);
    }
}

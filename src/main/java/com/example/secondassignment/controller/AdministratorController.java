package com.example.secondassignment.controller;

import com.example.secondassignment.model.DTO.AdministratorDTO;
import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.mappers.AdministratorMapper;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Category;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.service.account.administrator.AdministratorServiceImpl;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateFoodNameException;
import com.example.secondassignment.service.restaurant.food.FoodServiceImpl;
import com.example.secondassignment.service.restaurant.food.category.FoodCategoryServiceImpl;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import com.example.secondassignment.service.address.zone.ZoneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdministratorServiceImpl administratorService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private ZoneServiceImpl zoneService;

    @Autowired
    private FoodCategoryServiceImpl foodCategoryService;

    @Autowired
    private FoodServiceImpl foodService;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<AdministratorDTO> getAdministrators() {
        return administratorService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<AdministratorDTO> save(@Validated @RequestBody Administrator administrator) {
        try {
            return new ResponseEntity<>(AdministratorMapper.getInstance().convertToDTO(administratorService.save(administrator)),
                    HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/addRestaurant")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Zone>> getZones() {
        try {
            return new ResponseEntity<>(zoneService.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //restaurant operations
    @PostMapping("/addRestaurant")
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody(required = false)  RestaurantDTO restaurantDTO) throws
            InvalidDataException, DuplicateRestaurantNameException {
        return new ResponseEntity<>(restaurantService.save(restaurantDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteRestaurant")
    public ResponseEntity<String> deleteRestaurant(@RequestBody Integer id) {
        try {
            String msg = restaurantService.delete(id);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //food operations
    @GetMapping("/addFood")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Category>> getFoodCategories() {
        try {
            return new ResponseEntity<>(foodCategoryService.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/addFood")
    public ResponseEntity<FoodDTO> addFood(@RequestBody(required = false) FoodDTO foodDTO) throws InvalidDataException,
            DuplicateFoodNameException {
        return new ResponseEntity<>(foodService.save(foodDTO), HttpStatus.CREATED);
    }

    /*@GetMapping("/viewMenu")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<FoodDTO>> getFoodCategories(@Param("restaurantName") String restaurantName) {
        System.out.println(restaurantName);
        try {
            return new ResponseEntity<>(foodService.findByRestaurant(restaurantName), HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }*/

    //order operations

}

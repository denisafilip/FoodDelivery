package com.example.secondassignment.controller;

import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.DTO.LoginDTO;
import com.example.secondassignment.DTO.RestaurantDTO;
import com.example.secondassignment.mappers.AdministratorMapper;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.service.administrator.AdministratorServiceImpl;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
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

    /*@PostMapping("/")
    public ResponseEntity<AdministratorDTO> loginAdministrator(@Validated @RequestBody LoginDTO loginDTO) {
        try {
            return new ResponseEntity<>(administratorService.logIn(loginDTO), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }*/

    //restaurant operations
    @PostMapping(value = "/restaurant")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            RestaurantDTO _restaurant = restaurantService.save(restaurant);
            return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/restaurant")
    public ResponseEntity<String> deleteRestaurant(@RequestBody Integer id) {
        try {
            String msg = restaurantService.delete(id);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //food operations

    //order operations

}

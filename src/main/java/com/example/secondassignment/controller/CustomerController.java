package com.example.secondassignment.controller;

import com.example.secondassignment.model.DTO.CustomerDTO;
import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.service.account.customer.CustomerServiceImpl;
import com.example.secondassignment.service.address.zone.ZoneServiceImpl;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private ZoneServiceImpl zoneService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody(required = false) CustomerDTO customerDTO) {
        try {
            return new ResponseEntity<>(customerService.register(customerDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Zone>> register() {
        try {
            return new ResponseEntity<>(zoneService.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/viewRestaurants")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<RestaurantDTO>> findRestaurants() {
        try {
            return new ResponseEntity<>(restaurantService.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}

package com.example.secondassignment.controller;

import com.example.secondassignment.model.DTO.*;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.model.mappers.CustomerMapper;
import com.example.secondassignment.service.account.customer.CustomerServiceImpl;
import com.example.secondassignment.service.account.exceptions.DuplicateEmailException;
import com.example.secondassignment.service.address.zone.ZoneServiceImpl;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    private OrderServiceImpl orderService;

    @Autowired
    private ZoneServiceImpl zoneService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody(required = false) CustomerDTO customerDTO)
            throws InvalidDataException, DuplicateEmailException {
        return new ResponseEntity<>(customerService.register(customerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CustomerDTO getCurrentCustomer(@Param("customerEmail") String customerEmail) {
        return CustomerMapper.getInstance().convertToDTO(customerService.findByEmail(customerEmail));
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

    @PostMapping("/viewMenu")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody(required = false) OrderDTO orderDTO) throws InvalidDataException {
        return new ResponseEntity<>(orderService.save(orderDTO), HttpStatus.ACCEPTED);
    }

}

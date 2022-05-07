package com.example.secondassignment.controller;

import com.example.secondassignment.model.DTO.*;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.model.mappers.CustomerMapper;
import com.example.secondassignment.service.account.customer.CustomerServiceImpl;
import com.example.secondassignment.service.account.exceptions.DuplicateEmailException;
import com.example.secondassignment.service.address.zone.ZoneServiceImpl;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import com.example.secondassignment.service.restaurant.order.OrderServiceImpl;
import com.example.secondassignment.service.restaurant.order.ViewOrdersFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private ViewOrdersFacade viewOrdersFacade;

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody(required = false) CustomerDTO customerDTO)
            throws InvalidDataException, DuplicateEmailException {
        return new ResponseEntity<>(customerService.register(customerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CustomerDTO getCurrentCustomer(@Param("customerEmail") String customerEmail) {
        System.out.println(customerEmail);
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

    @GetMapping("/viewOrderHistory")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<OrderDTO>> getOrders(@Param("customerEmail") String customerEmail)
            throws NoSuchRestaurantException, InvalidDataException {
        return new ResponseEntity<>(viewOrdersFacade.findAllByRestaurantOrCustomer(customerEmail, null), HttpStatus.ACCEPTED);
    }

    @PostMapping("/viewMenu")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody(required = false) OrderDTO orderDTO) throws InvalidDataException {
        return new ResponseEntity<>(orderService.save(orderDTO), HttpStatus.ACCEPTED);
    }

}

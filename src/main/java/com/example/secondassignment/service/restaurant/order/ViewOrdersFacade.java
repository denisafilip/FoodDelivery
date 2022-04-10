package com.example.secondassignment.service.restaurant.order;

import com.example.secondassignment.model.DTO.OrderDTO;
import com.example.secondassignment.repository.OrderRepository;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewOrdersFacade {

    @Autowired
    private OrderServiceImpl orderService;

    public List<OrderDTO> findAllByRestaurantOrCustomer(String customerEmail, String restaurantName)
            throws NoSuchRestaurantException, NoSuchAccountException, InvalidDataException {
        if (customerEmail != null) {
            return orderService.findAllByCustomerEmail(customerEmail);
        } else if (restaurantName != null) {
            return orderService.findAllByRestaurantName(restaurantName);
        } else {
            throw new InvalidDataException("Both customer email and restaurant name cannot be null!");
        }
    }

}

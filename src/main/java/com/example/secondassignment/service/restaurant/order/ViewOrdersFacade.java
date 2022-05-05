package com.example.secondassignment.service.restaurant.order;

import com.example.secondassignment.model.DTO.OrderDTO;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Facade class for filtering the existing orders.
 */
@Service
public class ViewOrdersFacade {

    @Autowired
    private OrderServiceImpl orderService;

    /**
     * Filters the existing orders by either the customer email (for customer view) or by the restaurant name
     * (for administrator view).
     * @param customerEmail used to filter the orders
     * @param restaurantName used to filter the orders
     * @return A subset of the existing orders, matching the filtering criteria
     * @throws NoSuchRestaurantException If no restaurant with the given name exists.
     * @throws NoSuchAccountException If no account with the given email exists.
     * @throws InvalidDataException If the details are invalid.
     */
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

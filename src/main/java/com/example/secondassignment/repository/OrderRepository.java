package com.example.secondassignment.repository;

import com.example.secondassignment.model.Customer;
import com.example.secondassignment.model.DTO.OrderDTO;
import com.example.secondassignment.model.Order;
import com.example.secondassignment.model.OrderStatus;
import com.example.secondassignment.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<List<Order>> findAllByStatus(OrderStatus status);

    Optional<List<Order>> findAllByCustomer(Customer customer);

    Optional<List<Order>> findAllByRestaurant(Restaurant restaurant);

    Optional<List<Order>> findAllByRestaurantAndStatus(Restaurant restaurant, OrderStatus status);

}

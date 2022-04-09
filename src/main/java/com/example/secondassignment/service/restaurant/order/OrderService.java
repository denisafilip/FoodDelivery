package com.example.secondassignment.service.restaurant.order;

import com.example.secondassignment.model.DTO.CustomerDTO;
import com.example.secondassignment.model.DTO.OrderDTO;
import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.OrderStatus;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderService {

    @Transactional
    OrderDTO save(OrderDTO orderDTO) throws InvalidDataException, DuplicateRestaurantNameException;

    List<OrderDTO> findAll();

    List<OrderDTO> findAllByStatus(OrderStatus status);

    List<OrderDTO> findAllByCustomer(CustomerDTO customerDTO);

    List<OrderDTO> findAllByRestaurant(RestaurantDTO restaurantDTO);

    List<OrderDTO> findAllByRestaurantAndStatus(RestaurantDTO restaurantDTO, OrderStatus status);

    List<OrderDTO> findAllByRestaurantName(String restaurantName) throws NoSuchRestaurantException;

    List<OrderDTO> findAllByCustomerEmail(String customerEmail) throws NoSuchAccountException;

    OrderDTO updateOrderStatus(OrderStatus orderStatus, OrderDTO orderDTO) throws InvalidDataException;

    OrderDTO acceptOrder(OrderDTO orderDTO) throws InvalidDataException;

    OrderDTO declineOrder(OrderDTO orderDTO) throws InvalidDataException;
}

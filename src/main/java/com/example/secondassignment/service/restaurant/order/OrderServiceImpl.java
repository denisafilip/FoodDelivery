package com.example.secondassignment.service.restaurant.order;

import com.example.secondassignment.model.*;
import com.example.secondassignment.model.DTO.*;
import com.example.secondassignment.model.mappers.FoodMapper;
import com.example.secondassignment.model.mappers.OrderMapper;
import com.example.secondassignment.repository.OrderRepository;
import com.example.secondassignment.service.account.customer.CustomerServiceImpl;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import com.example.secondassignment.service.restaurant.food.FoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private FoodServiceImpl foodService;

    @Override
    public OrderDTO save(OrderDTO orderDTO) throws InvalidDataException {
        Customer customer = customerService.findByEmail(orderDTO.getCustomer().getEmail());

        if (customer == null) {
            throw new InvalidDataException("No suitable customer was found when placing the order.");
        }

        Restaurant restaurant = restaurantService.findByName(orderDTO.getRestaurant().getName());

        if (restaurant == null) {
            throw new InvalidDataException("No suitable restaurant was found when placing the order.");
        }

        if(!restaurant.getDeliveryZones().contains(customer.getAddress().getZone()))
            throw new InvalidDataException("Sorry! The restaurant " + restaurant.getName() + " does not deliver food to your address!");

        List<Food> foods = new ArrayList<>();
        for (FoodDTO foodDTO : orderDTO.getFoods()) {
            Food food = foodService.findByNameAndRestaurant(foodDTO.getName(), restaurant);
            if (food != null) {
                foods.add(food);
            }
        }

        Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .foods(foods)
                .date(LocalDate.now())
                .status(OrderStatus.PENDING)
                .total(foods.stream().mapToInt(Food::getPrice).sum())
                .build();
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.getInstance().convertToDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findAllByStatus(OrderStatus status) {
        Optional<List<Order>> orders = orderRepository.findAllByStatus(status);

        return orders.map(ordersList -> ordersList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public List<OrderDTO> findAllByCustomer(CustomerDTO customerDTO) {
        Customer customer = customerService.findByEmail(customerDTO.getEmail());

        Optional<List<Order>> orders = orderRepository.findAllByCustomer(customer);

        return orders.map(ordersList -> ordersList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public List<OrderDTO> findAllByRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantService.findByName(restaurantDTO.getName());

        Optional<List<Order>> orders = orderRepository.findAllByRestaurant(restaurant);

        return orders.map(ordersList -> ordersList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public List<OrderDTO> findAllByRestaurantAndStatus(RestaurantDTO restaurantDTO, OrderStatus status) {
        Restaurant restaurant = restaurantService.findByName(restaurantDTO.getName());

        Optional<List<Order>> orders = orderRepository.findAllByRestaurantAndStatus(restaurant, status);

        return orders.map(ordersList -> ordersList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public List<OrderDTO> findAllByRestaurantName(String restaurantName) throws NoSuchRestaurantException {
        Restaurant restaurant = restaurantService.findByName(restaurantName);

        if (restaurant == null) {
            throw new NoSuchRestaurantException("No restaurant with the name " + restaurantName + " exists.");
        }

        Optional<List<Order>> orders = orderRepository.findAllByRestaurant(restaurant);

        return orders.map(orderList -> orderList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public List<OrderDTO> findAllByCustomerEmail(String customerEmail) throws NoSuchAccountException {
        Customer customer = customerService.findByEmail(customerEmail);

        if (customer == null) {
            throw new NoSuchAccountException("No customer with the email " + customerEmail + " exists.");
        }

        Optional<List<Order>> orders = orderRepository.findAllByCustomer(customer);

        return orders.map(orderList -> orderList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }
}

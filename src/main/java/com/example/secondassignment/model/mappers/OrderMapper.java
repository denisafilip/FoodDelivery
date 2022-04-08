package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.OrderDTO;
import com.example.secondassignment.model.Order;

import java.util.stream.Collectors;

public class OrderMapper implements Mapper<Order, OrderDTO> {

    private static OrderMapper orderMapper = null;

    private OrderMapper() {

    }

    public static OrderMapper getInstance() {
        if (orderMapper == null) {
            orderMapper = new OrderMapper();
        }
        return orderMapper;
    }

    @Override
    public Order convertFromDTO(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO convertToDTO(Order order) {
        return OrderDTO.builder()
                .date(order.getDate())
                .orderStatus(order.getStatus())
                .customer(CustomerMapper.getInstance().convertToDTO(order.getCustomer()))
                .foods(order.getFoods().stream()
                        .map(FoodMapper.getInstance()::convertToDTO)
                        .collect(Collectors.toSet()))
                .restaurant(RestaurantMapper.getInstance().convertToDTO(order.getRestaurant()))
                .build();
    }
}

package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.OrderDTO;
import com.example.secondassignment.model.Order;

import java.util.stream.Collectors;

/**
 * Singleton class used for mapping an Order to an OrderDTO and vice-versa.
 */
public class OrderMapper implements Mapper<Order, OrderDTO> {

    /**
     * Singleton instance of the OrderMapper class.
     */
    private static OrderMapper orderMapper = null;

    /**
     * Constructor.
     */
    private OrderMapper() {

    }

    /**
     * Retrieves the single instance of the OrderMapper class.
     * @return the instance of the OrderMapper class.
     */
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
                .idOrder(order.getIdOrder())
                .status(order.getStatus())
                .customer(CustomerMapper.getInstance().convertToDTO(order.getCustomer()))
                .foods(order.getFoods().stream()
                        .map(FoodMapper.getInstance()::convertToDTO)
                        .collect(Collectors.toList()))
                .restaurant(RestaurantMapper.getInstance().convertToDTO(order.getRestaurant()))
                .total(order.getTotal())
                .build();
    }
}

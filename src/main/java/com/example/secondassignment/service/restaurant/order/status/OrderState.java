package com.example.secondassignment.service.restaurant.order.status;

import com.example.secondassignment.model.Order;
import com.example.secondassignment.model.OrderStatus;
import com.example.secondassignment.service.exceptions.InvalidDataException;

import java.util.Set;

public interface OrderState {

    default void changeState(Order order, OrderStatus status) throws InvalidDataException {
        if (getNextStatuses().contains(status)) {
            order.setStatus(status);
        } else {
            throw new InvalidDataException("The order cannot be changes to this status!");
        }

    }

    Set<OrderStatus> getNextStatuses();

}

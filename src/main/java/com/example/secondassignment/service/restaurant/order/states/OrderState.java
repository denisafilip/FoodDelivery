package com.example.secondassignment.service.restaurant.order.states;

import com.example.secondassignment.model.Order;
import com.example.secondassignment.model.OrderStatus;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import sun.security.x509.InvalidityDateExtension;

import java.util.Set;

public interface OrderState {

    default void changeState(Order order, OrderStatus status) throws InvalidDataException {
        if (getNextStates().contains(status)) {
            order.setStatus(status);
        } else {
            throw new InvalidDataException("The order cannot be transitioned in this state!");
        }

    }

    Set<OrderStatus> getNextStates();

}

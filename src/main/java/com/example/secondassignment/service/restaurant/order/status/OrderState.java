package com.example.secondassignment.service.restaurant.order.status;

import com.example.secondassignment.model.Order;
import com.example.secondassignment.model.OrderStatus;
import com.example.secondassignment.service.exceptions.InvalidDataException;

import java.util.Set;

/**
 * Interface representing the general format of an order status, used for the State Design Pattern.
 */
public interface OrderState {

    /**
     * Makes the transition from the current order status to a new valid status.
     * @param order whose status is changed
     * @param status to which the order status is changed
     * @throws InvalidDataException if the new status is invalid
     */
    default void changeState(Order order, OrderStatus status) throws InvalidDataException {
        if (getNextStatuses().contains(status)) {
            order.setStatus(status);
        } else {
            throw new InvalidDataException("The order cannot be changed to this status!");
        }

    }

    /**
     * Retrieves a set containing all valid statuses to which an order can be transitioned.
     * @return set containing the valid statuses
     */
    Set<OrderStatus> getNextStatuses();

}

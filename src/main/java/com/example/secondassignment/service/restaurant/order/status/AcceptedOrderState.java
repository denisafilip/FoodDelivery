package com.example.secondassignment.service.restaurant.order.status;

import com.example.secondassignment.model.OrderStatus;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for the Accepted order status
 */
@Getter
public class AcceptedOrderState implements OrderState {

    private final OrderStatus orderStatus = OrderStatus.ACCEPTED;

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<OrderStatus> getNextStatuses() {
        Set<OrderStatus> nextStates = new HashSet<>();
        nextStates.add(OrderStatus.IN_DELIVERY);
        return nextStates;
    }
}

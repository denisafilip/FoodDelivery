package com.example.secondassignment.service.restaurant.order.status;

import com.example.secondassignment.model.OrderStatus;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for the InDelivery order status
 */
@Getter
public class InDeliveryOrderState implements OrderState {

    private final OrderStatus orderStatus = OrderStatus.IN_DELIVERY;

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<OrderStatus> getNextStatuses() {
        Set<OrderStatus> nextStates = new HashSet<>();
        nextStates.add(OrderStatus.DELIVERED);
        return nextStates;
    }
}

package com.example.secondassignment.service.restaurant.order.status;

import com.example.secondassignment.model.OrderStatus;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for the Delivered order status
 */
@Getter
public class DeliveredOrderState implements OrderState {

    private final OrderStatus orderStatus = OrderStatus.DELIVERED;

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<OrderStatus> getNextStatuses() {
        return new HashSet<>();
    }
}

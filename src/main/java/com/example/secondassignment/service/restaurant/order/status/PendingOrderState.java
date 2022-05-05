package com.example.secondassignment.service.restaurant.order.status;

import com.example.secondassignment.model.OrderStatus;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for the Pending order status
 */
@Getter
public class PendingOrderState implements OrderState {

    private final OrderStatus orderStatus = OrderStatus.PENDING;

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<OrderStatus> getNextStatuses() {
        Set<OrderStatus> nextStates = new HashSet<>();
        nextStates.add(OrderStatus.ACCEPTED);
        nextStates.add(OrderStatus.DECLINED);
        return nextStates;
    }
}

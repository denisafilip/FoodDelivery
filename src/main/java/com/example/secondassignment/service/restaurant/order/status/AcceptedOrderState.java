package com.example.secondassignment.service.restaurant.order.status;

import com.example.secondassignment.model.OrderStatus;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class AcceptedOrderState implements OrderState {

    private final OrderStatus orderStatus = OrderStatus.ACCEPTED;

    @Override
    public Set<OrderStatus> getNextStatuses() {
        Set<OrderStatus> nextStates = new HashSet<>();
        nextStates.add(OrderStatus.IN_DELIVERY);
        return nextStates;
    }
}

package com.example.secondassignment.service.restaurant.order.states;

import com.example.secondassignment.model.OrderStatus;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class InDeliveryOrderState implements OrderState {

    private final OrderStatus orderStatus = OrderStatus.IN_DELIVERY;

    @Override
    public Set<OrderStatus> getNextStates() {
        Set<OrderStatus> nextStates = new HashSet<>();
        nextStates.add(OrderStatus.DELIVERED);
        return nextStates;
    }
}

package com.example.secondassignment.service.restaurant.order.states;

import com.example.secondassignment.model.OrderStatus;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class DeliveredOrderState implements OrderState {

    private final OrderStatus orderStatus = OrderStatus.DELIVERED;

    @Override
    public Set<OrderStatus> getNextStates() {
        return new HashSet<>();
    }
}

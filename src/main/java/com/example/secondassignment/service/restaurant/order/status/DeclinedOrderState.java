package com.example.secondassignment.service.restaurant.order.status;

import com.example.secondassignment.model.OrderStatus;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class DeclinedOrderState implements OrderState {

    private final OrderStatus orderStatus = OrderStatus.DECLINED;

    @Override
    public Set<OrderStatus> getNextStatuses() {
        return new HashSet<>();
    }
}

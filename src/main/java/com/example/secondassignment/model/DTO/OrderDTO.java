package com.example.secondassignment.model.DTO;

import com.example.secondassignment.model.Customer;
import com.example.secondassignment.model.Food;
import com.example.secondassignment.model.OrderStatus;
import com.example.secondassignment.model.Restaurant;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class OrderDTO {

    @NonNull
    private LocalDate date;

    @NonNull
    private Customer customer;

    @NonNull
    private Restaurant restaurant;

    @NonNull
    private OrderStatus orderStatus;

    @NonNull
    private List<Food> foods;

}

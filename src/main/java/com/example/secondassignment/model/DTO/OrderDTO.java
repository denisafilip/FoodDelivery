package com.example.secondassignment.model.DTO;

import com.example.secondassignment.model.OrderStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NonNull
    private LocalDate date;

    @NonNull
    private CustomerDTO customer;

    @NonNull
    private RestaurantDTO restaurant;

    @NonNull
    private OrderStatus orderStatus;

    @NonNull
    private List<FoodDTO> foods;

}

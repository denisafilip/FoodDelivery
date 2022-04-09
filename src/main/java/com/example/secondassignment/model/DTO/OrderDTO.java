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
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {

    private Integer idOrder;

    @NonNull
    private CustomerDTO customer;

    @NonNull
    private RestaurantDTO restaurant;

    private OrderStatus status;

    @NonNull
    private List<FoodDTO> foods;

    @NonNull
    private Integer total;

}

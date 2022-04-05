package com.example.secondassignment.model.DTO;

import com.example.secondassignment.model.Category;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FoodDTO {

    @NonNull
    private String name;

    @NonNull
    private Integer price;

    @NonNull
    private String description;

    @NonNull
    private Category category;

    @NonNull
    private RestaurantDTO restaurantDTO;
}

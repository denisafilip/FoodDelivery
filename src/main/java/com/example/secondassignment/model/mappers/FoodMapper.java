package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.Food;

public class FoodMapper implements Mapper<Food, FoodDTO> {
    private static FoodMapper foodMapper = null;

    private FoodMapper() {

    }

    public static FoodMapper getInstance() {
        if (foodMapper == null) {
            foodMapper = new FoodMapper();
        }
        return foodMapper;
    }

    @Override
    public Food convertFromDTO(FoodDTO foodDTO) {
        return null;
    }

    @Override
    public FoodDTO convertToDTO(Food food) {
        return FoodDTO.builder()
                .name(food.getName())
                .price(food.getPrice())
                .description(food.getDescription())
                .category(food.getCategory())
                .restaurantDTO(RestaurantMapper.getInstance().convertToDTO(food.getRestaurant()))
                .build();
    }
}

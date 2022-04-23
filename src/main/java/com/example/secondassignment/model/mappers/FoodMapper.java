package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.Food;

/**
 * Singleton class used for mapping a Food to a FoodDTO and vice-versa.
 */
public class FoodMapper implements Mapper<Food, FoodDTO> {

    /**
     * Singleton instance of the FoodMapper class.
     */
    private static FoodMapper foodMapper = null;

    /**
     * Constructor.
     */
    private FoodMapper() {

    }

    /**
     * Retrieves the single instance of the FoodMapper class.
     * @return the instance of the FoodMapper class.
     */
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

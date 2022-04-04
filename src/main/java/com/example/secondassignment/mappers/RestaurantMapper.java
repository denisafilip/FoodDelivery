package com.example.secondassignment.mappers;

import com.example.secondassignment.DTO.RestaurantDTO;
import com.example.secondassignment.model.Restaurant;

public class RestaurantMapper implements Mapper<Restaurant, RestaurantDTO> {

    private static RestaurantMapper restaurantMapper = null;

    private RestaurantMapper() {

    }

    public static RestaurantMapper getInstance() {
        if (restaurantMapper == null) {
            restaurantMapper = new RestaurantMapper();
        }
        return restaurantMapper;
    }


    @Override
    public Restaurant convertFromDTO(RestaurantDTO restaurantDTO) {
        return Restaurant.builder()
                .name(restaurantDTO.getName())
                .address(restaurantDTO.getAddress())
                .deliveryZones(restaurantDTO.getDeliveryZones())
                .address(restaurantDTO.getAddress())
                .build();
    }

    @Override
    public RestaurantDTO convertToDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .deliveryZones(restaurant.getDeliveryZones())
                .administrator(restaurant.getAdministrator())
                .build();
    }
}

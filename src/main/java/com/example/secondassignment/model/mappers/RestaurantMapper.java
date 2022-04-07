package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.Restaurant;

import java.util.stream.Collectors;

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
        return null;
    }

    @Override
    public RestaurantDTO convertToDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .deliveryZones(restaurant.getDeliveryZones().stream()
                        .map(ZoneMapper.getInstance()::convertToDTO)
                        .collect(Collectors.toSet()))
                .administratorDTO(AdministratorMapper.getInstance().convertToDTO(restaurant.getAdministrator()))
                .foods(restaurant.getFoods())
                .build();
    }
}

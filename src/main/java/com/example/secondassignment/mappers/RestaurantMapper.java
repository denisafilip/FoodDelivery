package com.example.secondassignment.mappers;

import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.DTO.RestaurantDTO;
import com.example.secondassignment.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                .build();
    }
}

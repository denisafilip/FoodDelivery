package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.Restaurant;

import java.util.stream.Collectors;

/**
 * Singleton class used for mapping a Restaurant to a RestaurantDTO and vice-versa.
 */
public class RestaurantMapper implements Mapper<Restaurant, RestaurantDTO> {

    /**
     * Singleton instance of the RestaurantMapper class.
     */
    private static RestaurantMapper restaurantMapper = null;

    /**
     * Constructor.
     */
    private RestaurantMapper() {

    }

    /**
     * Retrieves the single instance of the RestaurantMapper class.
     * @return the instance of the RestaurantMapper class.
     */
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

package com.example.secondassignment.service.restaurant;

import com.example.secondassignment.DTO.RestaurantDTO;
import com.example.secondassignment.mappers.RestaurantMapper;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public RestaurantDTO save(Restaurant restaurant) {
        // check if a restaurant already exists with that name?
        Restaurant _restaurant = restaurantRepository.save(restaurant);
        return RestaurantMapper.getInstance().convertToDTO(_restaurant);
    }

    @Override
    public List<RestaurantDTO> findAll() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String delete(Integer id) {

        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if(restaurant.isPresent()) {
            restaurantRepository.deleteById(id);
            return "Restaurant " + restaurant.get().getName() + " was deleted successfully!";
        }
        else return "There was no restaurant to delete!";
    }


}

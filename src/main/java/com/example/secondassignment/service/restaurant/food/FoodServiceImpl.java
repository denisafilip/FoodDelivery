package com.example.secondassignment.service.restaurant.food;

import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.mappers.AdministratorMapper;
import com.example.secondassignment.model.mappers.FoodMapper;
import com.example.secondassignment.model.*;
import com.example.secondassignment.repository.FoodRepository;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateFoodNameException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import com.example.secondassignment.service.validators.NameValidator;
import com.example.secondassignment.service.validators.NumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    public String validateFood(String name, Integer price) {
        try {
            new NameValidator().validate(name);
            new NumberValidator().validate(price);
            return null;
        } catch (InvalidDataException e) {
            return e.getMessage();
        }
    }

    @Override
    public Food findByNameAndRestaurant(String name, Restaurant restaurant) {
        Optional<Food> food = foodRepository.findByNameAndRestaurant(name, restaurant);
        return food.orElse(null);
    }

    @Override
    public List<FoodDTO> findByRestaurant(String restaurantName) throws NoSuchRestaurantException {
        Restaurant restaurant = restaurantService.findByName(restaurantName);

        if (restaurant == null) {
            throw new NoSuchRestaurantException("No restaurant with the name " + restaurantName + " exists.");
        }

        Optional<List<Food>> foods = foodRepository.findByRestaurant(restaurant);

        return foods.map(foodList -> foodList.stream()
                .map(FoodMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public FoodDTO save(FoodDTO foodDTO) throws DuplicateFoodNameException, InvalidDataException {
        Restaurant restaurant = restaurantService.findByName(foodDTO.getRestaurantDTO().getName());

        if (restaurant == null) {
            throw new NoSuchElementException("The restaurant with the name" + foodDTO.getRestaurantDTO().getName() + " does not exist!");
        }

        if (this.findByNameAndRestaurant(foodDTO.getName(), restaurant) != null) {
            throw new DuplicateFoodNameException("A food with the name " + foodDTO.getName() + " already exists for this restaurant!");
        }

        String validationMsg = validateFood(foodDTO.getName(), foodDTO.getPrice());
        if (validationMsg != null) {
            throw new InvalidDataException("The details of the food are incorrect!");
        }

        Food food = Food.builder()
                .name(foodDTO.getName())
                .price(foodDTO.getPrice())
                .description(foodDTO.getDescription())
                .category(foodDTO.getCategory())
                .restaurant(restaurant)
                .build();

        return FoodMapper.getInstance().convertToDTO(foodRepository.save(food));
    }


}

package com.example.secondassignment.service.restaurant.food;

import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.mappers.FoodMapper;
import com.example.secondassignment.model.*;
import com.example.secondassignment.repository.FoodRepository;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateFoodNameException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import com.example.secondassignment.service.restaurant.order.OrderServiceImpl;
import com.example.secondassignment.service.validators.NameValidator;
import com.example.secondassignment.service.validators.NumberValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class FoodServiceImpl implements FoodService {

    private final static Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class.getName());

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    /**
     * Validates the details of a food item.
     * @param name of the food item
     * @param price of the food item
     * @return an error message if the details are incorrect | null if they are correct
     */
    public String validateFood(String name, Integer price) {
        try {
            logger.info("Validate name {} and price {} of food", name, price);
            new NameValidator().validate(name);
            new NumberValidator().validate(price);
            return null;
        } catch (InvalidDataException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Food findByNameAndRestaurant(String name, Restaurant restaurant) {
        logger.info("Retrieve food with name {}, from restaurant {}", name, restaurant.getName());
        Optional<Food> food = foodRepository.findByNameAndRestaurant(name, restaurant);
        return food.orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FoodDTO> findByRestaurant(String restaurantName) throws NoSuchRestaurantException {
        Restaurant restaurant = restaurantService.findByName(restaurantName);

        if (restaurant == null) {
            logger.error("No restaurant with name {} was found", restaurantName);
            throw new NoSuchRestaurantException("No restaurant with the name " + restaurantName + " exists.");
        }

        Optional<List<Food>> foods = foodRepository.findByRestaurant(restaurant);

        return foods.map(foodList -> foodList.stream()
                .map(FoodMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodDTO save(FoodDTO foodDTO) throws DuplicateFoodNameException, InvalidDataException, NoSuchRestaurantException {
        logger.info("Create food {}", foodDTO);
        Restaurant restaurant = restaurantService.findByName(foodDTO.getRestaurantDTO().getName());

        if (restaurant == null) {
            logger.error("The restaurant with name {} was not found", foodDTO.getRestaurantDTO().getName());
            throw new NoSuchRestaurantException("The restaurant with the name" + foodDTO.getRestaurantDTO().getName() + " does not exist!");
        }

        if (this.findByNameAndRestaurant(foodDTO.getName(), restaurant) != null) {
            logger.error("Duplicate food with name {}", foodDTO.getName());
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

        Food savedFood = foodRepository.save(food);
        logger.info("Saved food {}", savedFood);

        return FoodMapper.getInstance().convertToDTO(savedFood);
    }


}

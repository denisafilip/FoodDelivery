package com.example.secondassignment.service.restaurant.food;

import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.Food;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateFoodNameException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Interface for the service of a food item.
 */
public interface FoodService {

    /**
     * Saves a food item to the database.
     * @param foodDTO containing the details of the food item to be added
     * @return The saved instance of the food item.
     * @throws DuplicateFoodNameException if a food item with the same name exists already
     * @throws InvalidDataException if the details of the food item are incorrect
     */
    @Transactional
    FoodDTO save(FoodDTO foodDTO) throws DuplicateFoodNameException, InvalidDataException, NoSuchRestaurantException;

    /**
     * Retrieves a food item by name and restaurant to which it belongs.
     * @param name of the food item
     * @param restaurant from where the food item can be ordered
     * @return the retrieved food item instance
     */
    Food findByNameAndRestaurant(String name, Restaurant restaurant);

    /**
     * Retrieves a food item by the restaurant to which it belongs.
     * @param restaurantName of the restaurant from where the food item can be ordered
     * @return the retrieved food item instance
     * @throws NoSuchRestaurantException if a restaurant with the given name cannot be found
     */
    List<FoodDTO> findByRestaurant(String restaurantName) throws NoSuchRestaurantException;
}

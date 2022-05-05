package com.example.secondassignment.service.restaurant;

import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Interface for the restaurant of an administrator.
 */
public interface RestaurantService {

    /**
     * Saves a new restaurant instance to the database.
     * @param restaurantDTO containing the details of the new restaurant
     * @return The newly saved restaurant instance.
     * @throws InvalidDataException If the details of the restaurant are invalid.
     * @throws DuplicateRestaurantNameException If a restaurant with the given name was already registered.
     */
    @Transactional
    RestaurantDTO save(RestaurantDTO restaurantDTO) throws InvalidDataException, DuplicateRestaurantNameException;

    /**
     * Retrieves all existing restaurants from the database.
     * @return List containing all existing restaurants.
     */
    List<RestaurantDTO> findAll();

    /**
     * Deletes a restaurant from the database.
     * @param id of the restaurant to be deleted
     * @return A message denoting the success/failure of the operation.
     */
    String delete(Integer id);

    /**
     * Retrieves a restaurant from the database by its name.
     * @param name of the restaurant
     * @return The found restaurant.
     */
    Restaurant findByName(String name);

    /**
     * Exports the menu of a restaurant to a PDF document.
     * @param restaurantName of the restaurant whose menu is exported
     * @return A ResponseEntity of the operation.
     * @throws FileNotFoundException If an error occurred during the generation of the PDF document.
     */
    ResponseEntity<InputStreamResource> exportMenuToPDF(String restaurantName) throws FileNotFoundException;
}

package com.example.secondassignment.service.restaurant.exceptions;

/**
 * Exception thrown when a restaurant does not exist already in the database.
 */
public class NoSuchRestaurantException extends Exception {

    /**
     * This constructor is called when the exception is thrown, displaying a detailed diagnosis of the error.
     *
     * @param message containing the details of the error
     */
    public NoSuchRestaurantException(String message) {
        super(message);
    }
}
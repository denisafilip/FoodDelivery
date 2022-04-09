package com.example.secondassignment.service.restaurant.exceptions;

public class DuplicateRestaurantNameException extends Exception {

    /**
     * This constructor is called when the exception is thrown, displaying a detailed diagnosis of the error.
     *
     * @param message containing the details of the error
     */
    public DuplicateRestaurantNameException(String message) {
        super(message);
    }
}
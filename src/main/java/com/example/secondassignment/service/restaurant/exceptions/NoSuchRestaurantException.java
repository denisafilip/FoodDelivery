package com.example.secondassignment.service.restaurant.exceptions;

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
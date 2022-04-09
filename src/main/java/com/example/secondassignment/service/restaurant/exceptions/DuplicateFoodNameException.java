package com.example.secondassignment.service.restaurant.exceptions;

public class DuplicateFoodNameException extends Exception{

    /**
     * This constructor is called when the exception is thrown, displaying a detailed diagnosis of the error.
     *
     * @param message containing the details of the error
     */
    public DuplicateFoodNameException(String message) {
        super(message);
    }
}
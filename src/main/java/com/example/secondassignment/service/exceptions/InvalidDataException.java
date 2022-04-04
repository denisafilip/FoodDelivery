package com.example.secondassignment.service.exceptions;

/**
 * InvalidDataException is an exception that is thrown when invalid data is inserted into the table of a database.
 */
public class InvalidDataException extends Exception{

    /**
     * This constructor is called when the exception is thrown, displaying a detailed diagnosis of the error.
     *
     * @param message containing the details of the error
     */
    public InvalidDataException(String message) {
        super(message);
    }
}

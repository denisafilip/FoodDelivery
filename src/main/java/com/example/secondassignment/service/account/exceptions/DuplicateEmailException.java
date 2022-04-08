package com.example.secondassignment.service.account.exceptions;

/**
 * DuplicateFoodNameException is an exception that is thrown when invalid data is inserted into the table of a database.
 */
public class DuplicateEmailException extends Exception{

    /**
     * This constructor is called when the exception is thrown, displaying a detailed diagnosis of the error.
     *
     * @param message containing the details of the error
     */
    public DuplicateEmailException(String message) {
        super(message);
    }
}
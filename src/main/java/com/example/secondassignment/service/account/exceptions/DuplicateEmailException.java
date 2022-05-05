package com.example.secondassignment.service.account.exceptions;

/**
 * Exception thrown if an email address exists already in the database.
 */
public class DuplicateEmailException extends Exception {

    /**
     * This constructor is called when the exception is thrown, displaying a detailed diagnosis of the error.
     *
     * @param message containing the details of the error
     */
    public DuplicateEmailException(String message) {
        super(message);
    }
}
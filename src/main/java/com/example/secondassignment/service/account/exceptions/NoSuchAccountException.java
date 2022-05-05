package com.example.secondassignment.service.account.exceptions;

/**
 * Exception thrown if an email and password combination does not match an existing account.
 */
public class NoSuchAccountException extends RuntimeException {

    /**
     * This constructor is called when the exception is thrown, displaying a detailed diagnosis of the error.
     *
     * @param message containing the details of the error
     */
    public NoSuchAccountException(String message) {
        super(message);
    }
}

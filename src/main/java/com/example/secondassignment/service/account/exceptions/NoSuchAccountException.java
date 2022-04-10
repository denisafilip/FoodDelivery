package com.example.secondassignment.service.account.exceptions;

public class NoSuchAccountException extends RuntimeException {

    public NoSuchAccountException(String message) {
        super(message);
    }
}

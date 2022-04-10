package com.example.secondassignment.service.account.exceptions;


public class DuplicateEmailException extends Exception{

    public DuplicateEmailException(String message) {
        super(message);
    }
}
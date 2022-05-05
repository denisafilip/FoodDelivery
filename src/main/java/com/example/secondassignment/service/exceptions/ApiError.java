package com.example.secondassignment.service.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * Class used for handling exceptions thrown by REST APIs.
 */
@Getter
@Setter
public class ApiError {

    /***
    status – the HTTP status code
    message – the error message associated with exception
    error – List of constructed error messages
    ***/
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }
}
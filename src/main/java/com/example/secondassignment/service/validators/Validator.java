package com.example.secondassignment.service.validators;

import com.example.secondassignment.service.exceptions.InvalidDataException;

/**
 * Validator is an interface responsible for validating the user input.
 *
 * @author Denisa Filip
 * @version $Id: $Id
 */
public interface Validator {

    /**
     * validate is the function that verifies if a field of a model class is correct and valid.
     *
     * @param obj - object that is validated
     * @throws InvalidDataException if the fields of the object obj contain invalid data.
     */
    void validate(Object obj) throws InvalidDataException;
}

package com.example.secondassignment.service.validators;

import com.example.secondassignment.service.exceptions.InvalidDataException;

public class NumberValidator implements Validator {
    @Override
    public void validate(Object obj) throws InvalidDataException {
        Integer number = (Integer) obj;
        if (number == null || number < 0) {
            throw new InvalidDataException("The number " + number + " is invalid!");
        }
    }
}

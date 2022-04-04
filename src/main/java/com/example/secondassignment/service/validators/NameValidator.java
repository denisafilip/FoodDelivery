package com.example.secondassignment.service.validators;

import com.example.secondassignment.service.exceptions.InvalidDataException;

import java.util.regex.Pattern;

public class NameValidator implements Validator {
    private static final String NAME_PATTERN = "[a-zA-Z- ăîâșț]+";

    /** {@inheritDoc} */
    @Override
    public void validate(Object nameObject) throws InvalidDataException {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        String name = (String) nameObject;
        if (name == null || name.isEmpty() || !pattern.matcher(name).matches()) {
            throw new InvalidDataException("The name " + name + " is not a valid name!");
        }
    }

}

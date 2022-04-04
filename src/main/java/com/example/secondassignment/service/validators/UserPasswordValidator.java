package com.example.secondassignment.service.validators;

import com.example.secondassignment.service.exceptions.InvalidDataException;

public class UserPasswordValidator implements Validator {
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-_@$!%*?&])[A-Za-z\\d@_$!%*?&]{6,}$";

    @Override
    public void validate(Object obj) throws InvalidDataException {
        String password = (String) obj;
        if (password == null || password.isEmpty() || !password.matches(PASSWORD_REGEX)) {
            throw new InvalidDataException("The password must have at least 6 characters, an uppercase letter, a lowercase letter, " +
                    "a special character and a digit.");
        }
    }

    public void checkIfSamePassword(String password, String confirmedPassword) throws InvalidDataException {
        if (!password.equals(confirmedPassword)) {
            throw new InvalidDataException("The two passwords do not match!");
        }
    }
}

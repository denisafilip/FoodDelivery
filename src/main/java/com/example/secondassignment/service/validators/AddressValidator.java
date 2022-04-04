package com.example.secondassignment.service.validators;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.service.exceptions.InvalidDataException;

import java.util.regex.Pattern;

public class AddressValidator implements Validator {
    private static final String POSTAL_CODE_PATTERN = "[0-9-]+";
    private static final String STREET_NUMBER_PATTERN = "[0-9a-zA-Z-]+";

    @Override
    public void validate(Object obj) throws InvalidDataException {
        Pattern postal_code_pattern = Pattern.compile(POSTAL_CODE_PATTERN);
        Pattern street_number_pattern = Pattern.compile(STREET_NUMBER_PATTERN);
        Address address = (Address) obj;

        new NameValidator().validate(address.getStreet());
        new NameValidator().validate(address.getCity());
        new NameValidator().validate(address.getCountry());

        if (address.getNumber() == null || address.getNumber().isEmpty() ||
                !street_number_pattern.matcher(address.getNumber()).matches()) {
            throw new InvalidDataException("The street number " + address.getNumber() + " is invalid!");
        }

        if (address.getPostalCode() == null || address.getPostalCode().isEmpty() ||
                !postal_code_pattern.matcher(address.getPostalCode()).matches()) {
            throw new InvalidDataException("The postal code " + address.getPostalCode() + " is invalid!");
        }
    }
}

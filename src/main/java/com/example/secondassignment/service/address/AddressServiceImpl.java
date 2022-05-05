package com.example.secondassignment.service.address;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.repository.AddressRepository;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.validators.AddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for the Address model.
 */
@Service
public class AddressServiceImpl implements AddressService {

    /**
     * Repository of the address, used for accessing the Address table in the database.
     */
    @Autowired
    private AddressRepository addressRepository;

    /**
     * Validates the fields of an address.
     * @param address to be validated
     * @return null if the Address is valid, or the respective error message if it is not
     */
    public String validateAddress(Address address) {
        try {
            new AddressValidator().validate(address);
            return null;
        } catch (InvalidDataException e) {
            return e.getMessage();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Address save(Address address) throws InvalidDataException {
        String validationMsg = validateAddress(address);
        if (validationMsg != null) {
            throw new InvalidDataException(validationMsg);
        }

        Optional<Address> addressOptional = addressRepository.findByStreetAndNumberAndCity(address.getStreet(), address.getNumber(), address.getCity());
        return addressOptional.orElseGet(() -> addressRepository.save(address));
    }
}

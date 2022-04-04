package com.example.secondassignment.service.address;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.repository.AddressRepository;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.validators.AddressValidator;
import com.example.secondassignment.service.validators.NameValidator;
import com.example.secondassignment.service.validators.UserEmailValidator;
import com.example.secondassignment.service.validators.UserPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public String validateAddress(Address address) {
        try {
            new AddressValidator().validate(address);
            return null;
        } catch (InvalidDataException e) {
            return e.getMessage();
        }
    }

    @Override
    public Address save(Address address) throws InvalidDataException {
        String validationMsg = validateAddress(address);
        if (validationMsg != null) {
            throw new InvalidDataException("The details of the address are incorrect!");
        }

        Optional<Address> addressOptional = addressRepository.findByStreetAndNumberAndCity(address.getStreet(), address.getNumber(), address.getCity());
        return addressOptional.orElseGet(() -> addressRepository.save(address));
    }
}

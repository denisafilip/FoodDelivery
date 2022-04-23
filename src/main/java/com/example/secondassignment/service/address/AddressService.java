package com.example.secondassignment.service.address;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.service.exceptions.InvalidDataException;

import javax.transaction.Transactional;

/**
 * Interface for the service of an address.
 */
public interface AddressService {
    
    @Transactional
    Address save(Address address) throws InvalidDataException;
}

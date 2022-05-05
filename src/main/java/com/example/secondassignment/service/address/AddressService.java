package com.example.secondassignment.service.address;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.service.exceptions.InvalidDataException;

import javax.transaction.Transactional;

/**
 * Interface for the service of an address.
 */
public interface AddressService {

    /**
     * Creates a new address in the database.
     * @param address to be added in the database
     * @return the saved address
     * @throws InvalidDataException if the address to be saved is not valid
     */
    @Transactional
    Address save(Address address) throws InvalidDataException;
}

package com.example.secondassignment.service.account.customer;


import com.example.secondassignment.model.DTO.CustomerDTO;
import com.example.secondassignment.model.Customer;
import com.example.secondassignment.service.account.exceptions.DuplicateEmailException;
import com.example.secondassignment.service.exceptions.InvalidDataException;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service interface for customer operations.
 */
public interface CustomerService {

    /**
     * Saves a customer instance to the database
     * @param customer saved in the database
     * @return the newly registered customer
     */
    @Transactional
    Customer save(Customer customer);

    /**
     * Retrieves all registered customers on the food delivery platform
     * @return all registered customers on the platform
     */
    List<CustomerDTO> findAll();

    /**
     * Retrieves a customer from the database by their email
     * @param email of the customer
     * @return the retrieved customer instance
     */
    Customer findByEmail(String email);

    /**
     * Registers a customer on the food delivery platform
     * @param customerDTO, containing the details of the customer to be registered
     * @return the registered customer
     * @throws InvalidDataException if the details of the customer are invalid
     * @throws DuplicateEmailException if an account with the same email exists already
     */
    CustomerDTO register(CustomerDTO customerDTO) throws InvalidDataException, DuplicateEmailException;
}

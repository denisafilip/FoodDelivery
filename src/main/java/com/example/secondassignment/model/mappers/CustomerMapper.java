package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.CustomerDTO;
import com.example.secondassignment.model.Customer;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Singleton class used for mapping a Customer to a CustomerDTO and vice-versa.
 */
public class CustomerMapper implements Mapper<Customer, CustomerDTO> {

    /**
     * Singleton instance of the CustomerMapper class.
     */
    private static CustomerMapper customerMapper = null;

    /**
     * Constructor.
     */
    private CustomerMapper() {

    }

    /**
     * Retrieves the single instance of the CustomerMapper class.
     * @return the instance of the CustomerMapper class.
     */
    public static CustomerMapper getInstance() {
        if (customerMapper == null) {
            customerMapper = new CustomerMapper();
        }
        return customerMapper;
    }

    @Override
    public Customer convertFromDTO(CustomerDTO customerDTO) {
        return Customer.CustomerBuilder()
                .address(customerDTO.getAddress())
                .email(customerDTO.getEmail())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .password(BCrypt.hashpw(customerDTO.getPassword(), BCrypt.gensalt(12)))
                .build();
    }

    @Override
    public CustomerDTO convertToDTO(Customer customer) {
        return CustomerDTO.CustomerDTOBuilder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .password(customer.getPassword())
                .build();
    }


}

package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.CustomerDTO;
import com.example.secondassignment.model.Customer;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.stream.Collectors;

public class CustomerMapper implements Mapper<Customer, CustomerDTO> {

    private static CustomerMapper customerMapper = null;

    private CustomerMapper() {

    }

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

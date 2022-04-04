package com.example.secondassignment.mappers;

import com.example.secondassignment.DTO.CustomerDTO;
import com.example.secondassignment.model.Customer;

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
        return null;
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

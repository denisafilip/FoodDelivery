package com.example.secondassignment.service.account.customer;


import com.example.secondassignment.model.DTO.CustomerDTO;
import com.example.secondassignment.model.Customer;
import com.example.secondassignment.service.account.exceptions.DuplicateEmailException;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerService {
    @Transactional
    Customer save(Customer customer);

    List<CustomerDTO> findAll();

    CustomerDTO findByEmail(String email);

    CustomerDTO register(CustomerDTO customerDTO) throws DuplicateName, InvalidDataException, DuplicateEmailException;
}

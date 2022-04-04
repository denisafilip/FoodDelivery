package com.example.secondassignment.service.customer;

import com.example.secondassignment.DTO.AccountDTO;
import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.DTO.CustomerDTO;
import com.example.secondassignment.DTO.LoginDTO;
import com.example.secondassignment.mappers.AdministratorMapper;
import com.example.secondassignment.mappers.CustomerMapper;
import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Customer;
import com.example.secondassignment.repository.CustomerRepository;
import com.example.secondassignment.service.address.AddressService;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.validators.NameValidator;
import com.example.secondassignment.service.validators.UserEmailValidator;
import com.example.secondassignment.service.validators.UserPasswordValidator;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressService addressService;

    public String validateCustomer(String firstName, String lastName, String email, String password) {
        try {
            new NameValidator().validate(firstName);
            new NameValidator().validate(lastName);
            new UserEmailValidator().validate(email);
            new UserPasswordValidator().validate(password);
            return null;
        } catch (InvalidDataException e) {
            return e.getMessage();
        }
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);

        return customer.map(value -> CustomerMapper.getInstance().convertToDTO(value)).orElse(null);
    }

    @Override
    public CustomerDTO register(CustomerDTO customerDTO) throws DuplicateName, InvalidDataException {
        Optional<Customer> _customer = customerRepository.findByEmail(customerDTO.getEmail());
        Address address = addressService.save(customerDTO.getAddress());

        if (_customer.isPresent()) {
            throw new DuplicateName("A customer with that email already exists!");
        }

        String validationMsg = validateCustomer(customerDTO.getFirstName(), customerDTO.getLastName(),
                customerDTO.getEmail(), customerDTO.getPassword());
        if (validationMsg != null) {
            throw new InvalidDataException("The details of the customer are incorrect!");
        }

        Customer c = Customer.CustomerBuilder()
                .address(address)
                .email(customerDTO.getEmail())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .password(BCrypt.hashpw(customerDTO.getPassword(), BCrypt.gensalt(12)))
                .build();

        customerRepository.save(c);
        return CustomerMapper.getInstance().convertToDTO(c);
    }

}

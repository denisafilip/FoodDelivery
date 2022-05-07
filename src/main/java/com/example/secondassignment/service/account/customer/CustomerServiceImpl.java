package com.example.secondassignment.service.account.customer;

import com.example.secondassignment.model.DTO.CustomerDTO;
import com.example.secondassignment.model.mappers.CustomerMapper;
import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Customer;
import com.example.secondassignment.repository.CustomerRepository;
import com.example.secondassignment.service.account.exceptions.DuplicateEmailException;
import com.example.secondassignment.service.address.AddressService;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.validators.NameValidator;
import com.example.secondassignment.service.validators.UserEmailValidator;
import com.example.secondassignment.service.validators.UserPasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class.getName());

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressService addressService;

    /**
     * Validates the details of a customer
     * @param firstName of the customer
     * @param lastName of the customer
     * @param email of the customer
     * @param password of the customer
     * @return null if the details are valid | error message if they are invalid
     */
    public String validateCustomer(String firstName, String lastName, String email, String password) {
        try {
            logger.info("Validate customer details {}, {}, {}", firstName, lastName, email);
            new NameValidator().validate(firstName);
            new NameValidator().validate(lastName);
            new UserEmailValidator().validate(email);
            new UserPasswordValidator().validate(password);
            return null;
        } catch (InvalidDataException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer save(Customer customer) {
        logger.info("Save customer {} {}, with email {}", customer.getFirstName(), customer.getLastName(), customer.getEmail());
        return customerRepository.save(customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CustomerDTO> findAll() {
        logger.info("Get all customers from the database");
        return customerRepository.findAll().stream()
                .map(CustomerMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer findByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);

        return customer.orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO register(CustomerDTO customerDTO) throws InvalidDataException, DuplicateEmailException {
        logger.info("Register customer {} {}, with email {}, to database", customerDTO.getFirstName(), customerDTO.getLastName(),
                customerDTO.getEmail());
        String validationMsg = validateCustomer(customerDTO.getFirstName(), customerDTO.getLastName(),
                customerDTO.getEmail(), customerDTO.getPassword());
        if (validationMsg != null) {
            throw new InvalidDataException(validationMsg);
        }
        Address address = addressService.save(customerDTO.getAddress());

        Optional<Customer> _customer = customerRepository.findByEmail(customerDTO.getEmail());

        if (_customer.isPresent()) {
            logger.error("A customer with the email {} already exists!", customerDTO.getEmail());
            throw new DuplicateEmailException("A customer with that email already exists!");
        }

        customerDTO.setAddress(address);

        Customer c = CustomerMapper.getInstance().convertFromDTO(customerDTO);
        customerRepository.save(c);
        return CustomerMapper.getInstance().convertToDTO(c);
    }

}

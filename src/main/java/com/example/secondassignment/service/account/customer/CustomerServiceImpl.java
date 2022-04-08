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
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
            e.printStackTrace();
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
    public Customer findByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);

        return customer.orElse(null);
    }

    @Override
    public CustomerDTO register(CustomerDTO customerDTO) throws InvalidDataException, DuplicateEmailException {
        String validationMsg = validateCustomer(customerDTO.getFirstName(), customerDTO.getLastName(),
                customerDTO.getEmail(), customerDTO.getPassword());
        if (validationMsg != null) {
            throw new InvalidDataException(validationMsg);
        }
        Address address = addressService.save(customerDTO.getAddress());

        Optional<Customer> _customer = customerRepository.findByEmail(customerDTO.getEmail());
        if (_customer.isPresent()) {
            throw new DuplicateEmailException("A customer with that email already exists!");
        }

        customerDTO.setAddress(address);

        Customer c = CustomerMapper.getInstance().convertFromDTO(customerDTO);
        customerRepository.save(c);
        return CustomerMapper.getInstance().convertToDTO(c);
    }

}

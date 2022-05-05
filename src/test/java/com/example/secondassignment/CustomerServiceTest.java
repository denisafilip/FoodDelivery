package com.example.secondassignment;


import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Customer;
import com.example.secondassignment.model.DTO.CustomerDTO;
import com.example.secondassignment.model.mappers.CustomerMapper;
import com.example.secondassignment.repository.CustomerRepository;
import com.example.secondassignment.service.account.customer.CustomerServiceImpl;
import com.example.secondassignment.service.account.exceptions.DuplicateEmailException;
import com.example.secondassignment.service.address.AddressService;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private final DummyTestObjects dummy = new DummyTestObjects();

    @Test
    public void testRegisterSuccess() throws InvalidDataException, DuplicateEmailException {
        CustomerDTO customerDTO = dummy.createTestCustomerDTO();

        Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(Optional.empty());
        Mockito.when(addressService.save(customerDTO.getAddress())).thenReturn(customerDTO.getAddress());

        CustomerDTO newCustomer = customerService.register(customerDTO);

        Assertions.assertTrue(BCrypt.checkpw(customerDTO.getPassword(), newCustomer.getPassword()));
        Assertions.assertEquals(customerDTO.getEmail(), newCustomer.getEmail());
    }

    @Test
    public void testRegisterDuplicateAccount() {
        Customer customer = dummy.createTestCustomer();
        CustomerDTO customerDTO = CustomerMapper.getInstance().convertToDTO(customer);

        Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(Optional.of(customer));
        Assertions.assertThrows(DuplicateEmailException.class, () -> customerService.register(customerDTO));
    }

    @Test
    public void testValidateCustomerSuccess() {
        String firstName = "TestFirst";
        String lastName = "TestLast";
        String password = "Password_123";
        String email = "test@gmail.com";

        Assertions.assertNull(customerService.validateCustomer(firstName, lastName, email, password));
    }

    @Test
    public void testValidateCustomerInvalidPassword() {
        String firstName = "TestFirst";
        String lastName = "TestLast";
        String password = "Password";
        String email = "test@gmail.com";
        Assertions.assertNotNull(customerService.validateCustomer(firstName, lastName, email, password));
    }

    @Test
    public void testValidateCustomerInvalidEmail() {
        String firstName = "TestFirst";
        String lastName = "TestLast";
        String password = "Password_123";
        String email = "test_gmail";
        Assertions.assertNotNull(customerService.validateCustomer(firstName, lastName, email, password));
    }

}

package com.example.secondassignment;

import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Customer;
import com.example.secondassignment.model.DTO.AccountDTO;
import com.example.secondassignment.model.DTO.LoginDTO;
import com.example.secondassignment.repository.AdministratorRepository;
import com.example.secondassignment.repository.CustomerRepository;
import com.example.secondassignment.service.account.AccountServiceImpl;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private final DummyTestObjects dummy = new DummyTestObjects();

    @Test
    public void testFindByEmailAdministrator() {
        Administrator administrator = dummy.createTestAdministrator();

        Mockito.when(administratorRepository.findByEmail(administrator.getEmail())).thenReturn(Optional.of(administrator));

        AccountDTO foundAccountDTO = accountService.findByEmail(administrator.getEmail());

        Assertions.assertEquals(administrator.getEmail(), foundAccountDTO.getEmail());

    }

    @Test
    public void testFindByEmailCustomer() {
        Customer customer = dummy.createTestCustomer();

        Mockito.when(administratorRepository.findByEmail(customer.getEmail())).thenReturn(Optional.empty());
        Mockito.when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        AccountDTO foundAccountDTO = accountService.findByEmail(customer.getEmail());

        Assertions.assertEquals(customer.getEmail(), foundAccountDTO.getEmail());
    }

    @Test(expected = NoSuchAccountException.class)
    public void testLoginNoAccount() {
        Administrator administrator = dummy.createTestAdministrator();

        Mockito.when(administratorRepository.findByEmail(administrator.getEmail())).thenReturn(Optional.empty());
        Mockito.when(customerRepository.findByEmail(administrator.getEmail())).thenReturn(Optional.empty());

        accountService.logIn(new LoginDTO(administrator.getEmail(), administrator.getPassword()));
    }
}

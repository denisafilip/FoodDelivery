package com.example.secondassignment.service.account;

import com.example.secondassignment.model.DTO.AccountDTO;
import com.example.secondassignment.model.DTO.LoginDTO;
import com.example.secondassignment.model.mappers.AccountMapper;
import com.example.secondassignment.model.mappers.AdministratorMapper;
import com.example.secondassignment.model.mappers.CustomerMapper;
import com.example.secondassignment.model.Account;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Customer;
import com.example.secondassignment.repository.AdministratorRepository;
import com.example.secondassignment.repository.CustomerRepository;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public AccountDTO findByEmail(String email) {
        Optional<Administrator> administrator = administratorRepository.findByEmail(email);
        if (administrator.isPresent()) {
            return administrator.map(value -> AdministratorMapper.getInstance().convertToDTO(value)).orElse(null);
        } else {
            Optional<Customer> customer = customerRepository.findByEmail(email);
            return customer.map(value -> CustomerMapper.getInstance().convertToDTO(value)).orElse(null);
        }
    }

    @Override
    public AccountDTO getAccountDTO(LoginDTO loginDTO) throws NoSuchAccountException {
        AccountDTO accountDTO = this.findByEmail(loginDTO.getEmail());
        if (accountDTO == null) {
            throw new NoSuchAccountException("No account with this email was found.");
        }
        Account account = AccountMapper.getInstance().convertFromDTO(accountDTO);

        if (BCrypt.checkpw(loginDTO.getPassword(), account.getPassword())) {
            return accountDTO;
        }
        throw new NoSuchAccountException("The password for this email is incorrect!");
    }

    public AccountDTO logIn(LoginDTO loginDTO) throws NoSuchAccountException {
        return this.getAccountDTO(loginDTO);
    }

}

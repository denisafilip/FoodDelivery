package com.example.secondassignment.service.account;

import com.example.secondassignment.DTO.AccountDTO;
import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.DTO.CustomerDTO;
import com.example.secondassignment.DTO.LoginDTO;
import com.example.secondassignment.mappers.AccountMapper;
import com.example.secondassignment.mappers.AdministratorMapper;
import com.example.secondassignment.mappers.CustomerMapper;
import com.example.secondassignment.model.Account;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Customer;
import com.example.secondassignment.repository.AdministratorRepository;
import com.example.secondassignment.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
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
    public AccountDTO getAccountDTO(LoginDTO loginDTO) {
        AccountDTO accountDTO = this.findByEmail(loginDTO.getEmail());
        if (accountDTO == null) {
            throw new NoSuchElementException("No account with this email was found.");
        }
        Account account = AccountMapper.getInstance().convertFromDTO(accountDTO);

        if (BCrypt.checkpw(loginDTO.getPassword(), account.getPassword())) {
            return accountDTO;
        }
        throw new NoSuchElementException("No account with this email and password was found.");
    }

    public AccountDTO logIn(LoginDTO loginDTO) {
        /*AccountDTO accountDTO = this.getAccountDTO(loginDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("account", accountDTO);
        boolean gotAdmin = false;
        if (accountDTO instanceof AdministratorDTO) {
            gotAdmin = true;
        }
        responseMap.put("admin", gotAdmin);
        return responseMap;*/
        return this.getAccountDTO(loginDTO);
    }

}

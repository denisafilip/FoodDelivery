package com.example.secondassignment.service.account;

import com.example.secondassignment.controller.AccountController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class that implements the methods for the user account management
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class.getName());

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Retrieves an user account from the database by email
     * @param email of the account
     * @return the DTO of the retrieved account
     */
    @Override
    public AccountDTO findByEmail(String email) {
        logger.info("Get account with email {}", email);
        Optional<Administrator> administrator = administratorRepository.findByEmail(email);
        if (administrator.isPresent()) {
            return administrator.map(value -> AdministratorMapper.getInstance().convertToDTO(value)).orElse(null);
        } else {
            Optional<Customer> customer = customerRepository.findByEmail(email);
            if (customer.isPresent()) {
                return customer.map(value -> CustomerMapper.getInstance().convertToDTO(value)).orElse(null);
            }
        }
        return null;
    }

    /**
     * Retrieves an user account from the database by email and password
     * @param loginDTO containing the email and password of the account
     * @return the DTO of the retrieved account
     */
    @Override
    public AccountDTO getAccountDTO(LoginDTO loginDTO) throws NoSuchAccountException {
        AccountDTO accountDTO = this.findByEmail(loginDTO.getEmail());
        if (accountDTO == null) {
            logger.warn("No account with email {} was found", loginDTO.getEmail());
            throw new NoSuchAccountException("No account with this email was found.");
        }
        Account account = AccountMapper.getInstance().convertFromDTO(accountDTO);
        if (BCrypt.checkpw(loginDTO.getPassword(), account.getPassword())) {
            return accountDTO;
        }
        logger.warn("The password for the email {} is incorrect!", loginDTO.getEmail());
        throw new NoSuchAccountException("The password for this email is incorrect!");
    }

    /**
     * Logins an account, by trying to match the given credentials with those from the database
     * @param loginDTO containing the email and password of the account
     * @return the DTO of the logged in account
     */
    @Override
    public AccountDTO logIn(LoginDTO loginDTO) throws NoSuchAccountException {
        return this.getAccountDTO(loginDTO);
    }

}

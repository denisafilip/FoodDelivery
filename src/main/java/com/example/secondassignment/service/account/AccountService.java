package com.example.secondassignment.service.account;

import com.example.secondassignment.model.DTO.AccountDTO;
import com.example.secondassignment.model.DTO.LoginDTO;

/**
 * Service interface for user account operations.
 */
public interface AccountService {

    /**
     * Retrieves an user account from the database by email
     * @param email of the account
     * @return the DTO of the retrieved account
     */
    AccountDTO findByEmail(String email);

    /**
     * Retrieves an user account from the database by email and password
     * @param loginDTO containing the email and password of the account
     * @return the DTO of the retrieved account
     */
    AccountDTO getAccountDTO(LoginDTO loginDTO);

    /**
     * Logins an account, by trying to match the given credentials with those from the database
     * @param loginDTO containing the email and password of the account
     * @return the DTO of the logged in account
     */
    AccountDTO logIn(LoginDTO loginDTO);
}

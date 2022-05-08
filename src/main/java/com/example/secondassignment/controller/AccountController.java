package com.example.secondassignment.controller;

import com.example.secondassignment.model.AccountRole;
import com.example.secondassignment.model.DTO.*;
import com.example.secondassignment.service.account.AccountServiceImpl;
import com.example.secondassignment.service.security.JwtResponse;
import com.example.secondassignment.service.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/login")
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class.getName());

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Logins an account on the platform
     * @param loginDTO - credentials of the current account
     * @return response received after account log in
     */
    @PostMapping("")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody(required = false) LoginDTO loginDTO) {
        String jwt = jwtUtils.generateJwtToken(loginDTO.getEmail());

        AccountDTO accountDTO = accountService.logIn(loginDTO);
        
        String role = null;
        boolean hasRestaurant = false;
        if (accountDTO instanceof AdministratorDTO) {
            role = AccountRole.ROLE_ADMIN.toString();
            if (((AdministratorDTO) accountDTO).getRestaurant() != null) {
                hasRestaurant = true;
            }
        } else if (accountDTO instanceof CustomerDTO) {
             role = AccountRole.ROLE_CUSTOMER.toString();
        }

        logger.info("Generated JWT token {}", jwt);
        return new ResponseEntity<>(new JwtResponse(jwt, accountDTO.getEmail(), role, hasRestaurant), HttpStatus.OK);
    }
}

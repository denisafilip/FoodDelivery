package com.example.secondassignment.controller;

import com.example.secondassignment.model.DTO.AccountDTO;
import com.example.secondassignment.model.DTO.LoginDTO;
import com.example.secondassignment.service.account.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @PostMapping("")
    public ResponseEntity<AccountDTO> login(@RequestBody(required = false) LoginDTO loginDTO) {
        return new ResponseEntity<>(accountService.logIn(loginDTO), HttpStatus.CREATED);
    }
}

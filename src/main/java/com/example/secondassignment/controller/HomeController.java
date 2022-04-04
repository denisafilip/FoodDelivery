package com.example.secondassignment.controller;

import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.service.account.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private AccountServiceImpl accountService;


    @GetMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String getAdministrators() {
        return "Hello there!";
    }
}

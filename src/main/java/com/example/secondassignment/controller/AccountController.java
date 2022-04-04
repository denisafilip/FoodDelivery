package com.example.secondassignment.controller;

import com.example.secondassignment.DTO.AccountDTO;
import com.example.secondassignment.DTO.LoginDTO;
import com.example.secondassignment.mappers.AccountMapper;
import com.example.secondassignment.service.account.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    /*@GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Map<String, Object>> login(@Param("credentials") LoginDTO loginDTO) {
        try {
            return new ResponseEntity<>(accountService.logIn(loginDTO), HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }*/

    @PostMapping("")
    public ResponseEntity<AccountDTO> login(@RequestBody(required = false) LoginDTO loginDTO) {
        try {
            System.out.println(loginDTO);
            AccountDTO accountDTO = accountService.logIn(loginDTO);
            System.out.println(accountDTO);
            /*Administrator administrator1 = administratorService.save(Administrator.AdminBuilder()
                    .firstName(administrator.getFirstName())
                    .lastName(administrator.getLastName())
                    .email(administrator.getEmail())
                    .password(administrator.getPassword())
                    .build());*/
            return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}

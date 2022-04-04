package com.example.secondassignment.service.account;

import com.example.secondassignment.DTO.AccountDTO;
import com.example.secondassignment.DTO.LoginDTO;

import java.util.Map;

public interface AccountService {

    AccountDTO findByEmail(String email);

    AccountDTO getAccountDTO(LoginDTO loginDTO);

    AccountDTO logIn(LoginDTO loginDTO);
}

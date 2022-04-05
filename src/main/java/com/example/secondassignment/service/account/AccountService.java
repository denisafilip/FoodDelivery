package com.example.secondassignment.service.account;

import com.example.secondassignment.model.DTO.AccountDTO;
import com.example.secondassignment.model.DTO.LoginDTO;

public interface AccountService {

    AccountDTO findByEmail(String email);

    AccountDTO getAccountDTO(LoginDTO loginDTO);

    AccountDTO logIn(LoginDTO loginDTO);
}

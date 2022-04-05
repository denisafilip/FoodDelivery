package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.AccountDTO;
import com.example.secondassignment.model.Account;

public class AccountMapper implements Mapper<Account, AccountDTO> {

    private static AccountMapper accountMapper = null;

    private AccountMapper() {

    }

    public static AccountMapper getInstance() {
        if (accountMapper == null) {
            accountMapper = new AccountMapper();
        }
        return accountMapper;
    }

    @Override
    public Account convertFromDTO(AccountDTO accountDTO) {
        return Account.builder()
                .firstName(accountDTO.getFirstName())
                .lastName(accountDTO.getLastName())
                .email(accountDTO.getEmail())
                .password(accountDTO.getPassword())
                .build();
    }

    @Override
    public AccountDTO convertToDTO(Account account) {
        return AccountDTO.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .password(account.getPassword())
                .build();
    }
}

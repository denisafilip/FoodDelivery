package com.example.secondassignment.service.account.administrator;

import com.example.secondassignment.model.DTO.AdministratorDTO;
import com.example.secondassignment.model.Administrator;

import javax.transaction.Transactional;
import java.util.List;

public interface AdministratorService {
    @Transactional
    Administrator save(Administrator administrator);

    List<AdministratorDTO> findAll();

    Administrator findByEmail(String email);
}

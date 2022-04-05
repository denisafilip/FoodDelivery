package com.example.secondassignment.service.administrator;

import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.DTO.LoginDTO;
import com.example.secondassignment.model.Administrator;

import javax.transaction.Transactional;
import java.util.List;

public interface AdministratorService {
    @Transactional
    Administrator save(Administrator administrator);

    List<AdministratorDTO> findAll();

    Administrator findByEmail(String email);
}

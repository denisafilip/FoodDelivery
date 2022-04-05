package com.example.secondassignment.service.administrator;

import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.DTO.LoginDTO;
import com.example.secondassignment.mappers.AdministratorMapper;
import com.example.secondassignment.mappers.CustomerMapper;
import com.example.secondassignment.mappers.RestaurantMapper;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Customer;
import com.example.secondassignment.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public Administrator save(Administrator administrator) {
        Administrator a = Administrator.AdminBuilder()
                .email(administrator.getEmail())
                .firstName(administrator.getFirstName())
                .lastName(administrator.getLastName())
                .password(BCrypt.hashpw(administrator.getPassword(), BCrypt.gensalt(12)))
                .build();
        return administratorRepository.save(a);
    }

    @Override
    public List<AdministratorDTO> findAll() {
        return administratorRepository.findAll().stream()
                .map(AdministratorMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Administrator findByEmail(String email) {
        Optional<Administrator> administrator = administratorRepository.findByEmail(email);

        return administrator.orElse(null);
    }

}

package com.example.secondassignment.service.account.administrator;

import com.example.secondassignment.model.DTO.AdministratorDTO;
import com.example.secondassignment.model.DTO.OrderDTO;
import com.example.secondassignment.model.OrderStatus;
import com.example.secondassignment.model.mappers.AdministratorMapper;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.repository.AdministratorRepository;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
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

package com.example.secondassignment.service.account.administrator;

import com.example.secondassignment.model.DTO.AdministratorDTO;
import com.example.secondassignment.model.mappers.AdministratorMapper;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AdministratorDTO> findAll() {
        return administratorRepository.findAll().stream()
                .map(AdministratorMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Administrator findByEmail(String email) {
        Optional<Administrator> administrator = administratorRepository.findByEmail(email);

        return administrator.orElse(null);
    }
}

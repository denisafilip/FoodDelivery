package com.example.secondassignment.mappers;

import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.DTO.CustomerDTO;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.repository.AdministratorRepository;
import com.example.secondassignment.service.administrator.AdministratorService;
import com.example.secondassignment.service.administrator.AdministratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class AdministratorMapper implements Mapper<Administrator, AdministratorDTO> {

    private static AdministratorMapper administratorMapper = null;

    private AdministratorMapper() {

    }

    public static AdministratorMapper getInstance() {
        if (administratorMapper == null) {
            administratorMapper = new AdministratorMapper();
        }
        return administratorMapper;
    }

    @Override
    public Administrator convertFromDTO(AdministratorDTO administratorDTO) {
        //System.out.println(administratorDTO);
        //return administratorService.findByEmail(administratorDTO.getEmail());
        return null;
        //return admin.orElse(null);
    }

    @Override
    public AdministratorDTO convertToDTO(Administrator administrator) {
        return AdministratorDTO.AdministratorDTOBuilder()
                .firstName(administrator.getFirstName())
                .lastName(administrator.getLastName())
                .email(administrator.getEmail())
                .password(administrator.getPassword())
                .restaurant(administrator.getRestaurant())
                .build();
    }
}

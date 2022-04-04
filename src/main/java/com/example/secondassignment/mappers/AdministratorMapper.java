package com.example.secondassignment.mappers;

import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.DTO.CustomerDTO;
import com.example.secondassignment.model.Administrator;

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
        return null;
    }

    @Override
    public AdministratorDTO convertToDTO(Administrator administrator) {
        return AdministratorDTO.AdministratorDTOBuilder()
                .firstName(administrator.getFirstName())
                .lastName(administrator.getLastName())
                .email(administrator.getEmail())
                .password(administrator.getPassword())
                .build();
    }
}

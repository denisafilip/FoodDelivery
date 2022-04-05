package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.AdministratorDTO;
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

package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.AdministratorDTO;
import com.example.secondassignment.model.Administrator;

/**
 * Singleton class used for mapping an Administrator to an AdministratorDTO and vice-versa.
 */
public class AdministratorMapper implements Mapper<Administrator, AdministratorDTO> {

    /**
     * Singleton instance of the AdministratorMapper class.
     */
    private static AdministratorMapper administratorMapper = null;

    /**
     * Constructor.
     */
    private AdministratorMapper() {

    }

    /**
     * Retrieves the single instance of the AdministratorMapper class.
     * @return the instance of the AdministratorMapper class.
     */
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
                .restaurant(administrator.getRestaurant())
                .build();
    }
}

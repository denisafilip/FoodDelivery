package com.example.secondassignment.service.account.administrator;

import com.example.secondassignment.model.DTO.AdministratorDTO;
import com.example.secondassignment.model.Administrator;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service interface for administrator operations.
 */
public interface AdministratorService {
    /**
     * Saves an administrator instance to the database
     * @param administrator to be saved
     * @return the saved administrator instance
     */
    @Transactional
    Administrator save(Administrator administrator);

    /**
     * Retrieves all restaurant administrators from the database
     * @return list containing all restaurant administrators
     */
    List<AdministratorDTO> findAll();

    /**
     * Retrieves an administrator from the database by email
     * @param email of the administrator
     * @return the found administrator
     */
    Administrator findByEmail(String email);
}

package com.example.secondassignment.service.address.zone;

import com.example.secondassignment.model.Zone;

import java.util.List;

/**
 * Service interface used to handle the Zone model
 */
public interface ZoneService {
    /**
     * Retrieves all the existing delivery zones from the database.
     * @return A list containing all delivery zones.
     */
    List<Zone> findAll();

    /**
     * Retrieves a zone from the database by its name.
     * @param name of the zone
     * @return The retrieved zone from the database.
     */
    Zone findByName(String name);
}

package com.example.secondassignment.service.address.zone;

import com.example.secondassignment.model.Zone;
import com.example.secondassignment.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Zone findByName(String name) {
        Optional<Zone> zone = zoneRepository.findByName(name);

        return zone.orElse(null);
    }
}

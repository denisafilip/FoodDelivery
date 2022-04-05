package com.example.secondassignment.service.zone;

import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.mappers.AdministratorMapper;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.repository.AdministratorRepository;
import com.example.secondassignment.repository.ZoneRepository;
import com.example.secondassignment.service.administrator.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }

    @Override
    public Zone findByName(String name) {
        Optional<Zone> zone = zoneRepository.findByName(name);

        return zone.orElse(null);
    }
}

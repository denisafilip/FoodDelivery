package com.example.secondassignment.service.address.zone;

import com.example.secondassignment.model.Zone;

import java.util.List;

public interface ZoneService {
    List<Zone> findAll();

    Zone findByName(String name);
}
package com.example.secondassignment.repository;

import com.example.secondassignment.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Integer> {
    Optional<Zone> findByName(String name);
}

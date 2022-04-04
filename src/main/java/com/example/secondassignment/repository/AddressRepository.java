package com.example.secondassignment.repository;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findByStreetAndNumberAndCity(String street, String number, String city);
}

package com.example.secondassignment.repository;

import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
}

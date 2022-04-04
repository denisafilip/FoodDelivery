package com.example.secondassignment.repository;

import com.example.secondassignment.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Integer deleteByName(String name);

}

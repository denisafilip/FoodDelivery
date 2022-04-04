package com.example.secondassignment.service.restaurant;

import com.example.secondassignment.DTO.RestaurantDTO;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    @Transactional
    RestaurantDTO save(RestaurantDTO restaurantDTO) throws DuplicateName, InvalidDataException;

    List<RestaurantDTO> findAll();

    String delete(Integer id);

    RestaurantDTO findByName(String name);
}

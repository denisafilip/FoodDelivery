package com.example.secondassignment.service.restaurant;

import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.DTO.ZoneDTO;
import com.example.secondassignment.model.mappers.RestaurantMapper;
import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.repository.RestaurantRepository;
import com.example.secondassignment.service.address.AddressService;
import com.example.secondassignment.service.account.administrator.AdministratorServiceImpl;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import com.example.secondassignment.service.validators.NameValidator;
import com.example.secondassignment.service.address.zone.ZoneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AdministratorServiceImpl administratorService;

    @Autowired
    private ZoneServiceImpl zoneService;

    public String validateRestaurant(String name) {
        try {
            new NameValidator().validate(name);
            return null;
        } catch (InvalidDataException e) {
            return e.getMessage();
        }
    }

    @Override
    public Restaurant findByName(String name) {
        Optional<Restaurant> restaurant = restaurantRepository.findByName(name);
        return restaurant.orElse(null);
    }

    @Override
    public RestaurantDTO save(RestaurantDTO restaurantDTO) throws InvalidDataException, DuplicateRestaurantNameException {
        if (this.findByName(restaurantDTO.getName()) != null) {
            throw new DuplicateRestaurantNameException("A restaurant with the name " + restaurantDTO.getName() + " already exists!");
        }

        Administrator administrator = administratorService.findByEmail(restaurantDTO.getAdministratorDTO().getEmail());
        if (administrator == null) {
            throw new NoSuchElementException("No corresponding administrator was found!");
        }

        Address address = addressService.save(restaurantDTO.getAddress());

        Set<Zone> zones = new HashSet<>();
        for (ZoneDTO zoneDTO : restaurantDTO.getDeliveryZones()) {
            Zone zone = zoneService.findByName(zoneDTO.getName());
            if (zone != null) {
                zones.add(zone);
            }
        }

        String validationMsg = validateRestaurant(restaurantDTO.getName());
        if (validationMsg != null) {
            throw new InvalidDataException("The details of the restaurant are incorrect!");
        }

        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDTO.getName())
                .address(address)
                .administrator(administrator)
                .deliveryZones(zones)
                .build();

        return RestaurantMapper.getInstance().convertToDTO(restaurantRepository.save(restaurant));
    }

    @Override
    public List<RestaurantDTO> findAll() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String delete(Integer id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if(restaurant.isPresent()) {
            restaurantRepository.deleteById(id);
            return "Restaurant " + restaurant.get().getName() + " was deleted successfully!";
        }
        else return "There was no restaurant to delete!";
    }


}

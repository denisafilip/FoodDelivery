package com.example.secondassignment;

import com.example.secondassignment.model.*;
import com.example.secondassignment.model.DTO.*;
import com.example.secondassignment.model.mappers.FoodMapper;
import com.example.secondassignment.model.mappers.OrderMapper;
import com.example.secondassignment.model.mappers.RestaurantMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DummyTestObjects {

    public AccountDTO createTestAccountDTO() {
        return AccountDTO.builder()
                .email("test@gmail.com")
                .firstName("Andreea")
                .lastName("Ionescu")
                .password("Password_123")
                .build();
        //BCrypt.hashpw("Password_123", BCrypt.gensalt(12))
    }

    public AdministratorDTO createTestAdministratorDTO() {
        AccountDTO accountDTO = createTestAccountDTO();
        return AdministratorDTO.AdministratorDTOBuilder()
                .email(accountDTO.getEmail())
                .firstName(accountDTO.getFirstName())
                .lastName(accountDTO.getLastName())
                .password(accountDTO.getPassword())
                .build();
    }

    public Administrator createTestAdministrator() {
        AccountDTO accountDTO = createTestAccountDTO();
        Administrator admin = Administrator.AdminBuilder()
                .email(accountDTO.getEmail())
                .firstName(accountDTO.getFirstName())
                .lastName(accountDTO.getLastName())
                .password(accountDTO.getPassword())
                .build();
        admin.setIdUser(1);
        return admin;
    }

    public Customer createTestCustomer() {
        AccountDTO accountDTO = createTestAccountDTO();
        Customer customer = Customer.CustomerBuilder()
                .email(accountDTO.getEmail())
                .firstName(accountDTO.getFirstName())
                .lastName(accountDTO.getLastName())
                .password(accountDTO.getPassword())
                .address(createTestAddress())
                .build();
        customer.setIdUser(1);
        return customer;
    }

    public CustomerDTO createTestCustomerDTO() {
        AccountDTO accountDTO = createTestAccountDTO();
        return CustomerDTO.CustomerDTOBuilder()
                .email(accountDTO.getEmail())
                .firstName(accountDTO.getFirstName())
                .lastName(accountDTO.getLastName())
                .password(accountDTO.getPassword())
                .address(createTestAddress())
                .build();
    }

    public Zone createTestZone() {
        return Zone.builder()
                .idZone(1)
                .name("Observator")
                .build();
    }

    public Set<ZoneDTO> createTestDeliveryZoneDTOs() {
        Set<ZoneDTO> zones = new HashSet<>();
        zones.add(ZoneDTO.builder().name("Observator").build());
        zones.add(ZoneDTO.builder().name("Marasti").build());
        zones.add(ZoneDTO.builder().name("Gheorgheni").build());
        return zones;
    }

    public Set<Zone> createTestDeliveryZones() {
        Set<Zone> zones = new HashSet<>();
        zones.add(createTestZone());
        zones.add(Zone.builder().idZone(2).name("Marasti").build());
        zones.add(Zone.builder().idZone(3).name("Gheorgheni").build());
        return zones;
    }

    public Address createTestAddress() {
        return Address.builder()
                .idAddress(1)
                .street("Doina")
                .number("21B")
                .city("Oradea")
                .country("Romania")
                .postalCode("123456")
                .zone(createTestZone())
                .build();
    }

    public Restaurant createTestRestaurant() {
        return Restaurant.builder()
                .idRestaurant(1)
                .administrator(createTestAdministrator())
                .name("Test Restaurant")
                .deliveryZones(createTestDeliveryZones())
                .address(createTestAddress())
                .build();
    }

    public RestaurantDTO createTestRestaurantDTO() {
        return RestaurantMapper.getInstance().convertToDTO(createTestRestaurant());
    }

    public Food createTestFood() {
        return Food.builder()
                .idFood(1)
                .name("Vegetarian Lasagna")
                .price(20)
                .description("Still has some cheese")
                .category(Category.builder().idCategory(1).name("Lunch").build())
                .restaurant(createTestRestaurant())
                .build();
    }

    public List<Food> createTestFoodList() {
        List<Food> foods = new ArrayList<>();
        foods.add(createTestFood());
        return foods;
    }

    public FoodDTO createTestFoodDTO() {
        return FoodMapper.getInstance().convertToDTO(createTestFood());
    }

    public Order createTestOrder() {
        return Order.builder()
                .idOrder(1)
                .customer(createTestCustomer())
                .foods(createTestFoodList())
                .date(LocalDate.now())
                .restaurant(createTestRestaurant())
                .status(OrderStatus.PENDING)
                .total(100)
                .build();
    }
}



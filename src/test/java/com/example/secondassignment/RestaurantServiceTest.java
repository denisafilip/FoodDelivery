package com.example.secondassignment;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.Food;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.model.mappers.RestaurantMapper;
import com.example.secondassignment.repository.RestaurantRepository;
import com.example.secondassignment.service.account.administrator.AdministratorServiceImpl;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import com.example.secondassignment.service.address.AddressService;
import com.example.secondassignment.service.address.zone.ZoneServiceImpl;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private AdministratorServiceImpl administratorService;

    @Mock
    private ZoneServiceImpl zoneService;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private final DummyTestObjects dummy = new DummyTestObjects();

    @Test
    public void testSave_Success() throws InvalidDataException {
        Restaurant restaurant = dummy.createTestRestaurant();

        Mockito.when(restaurantRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(administratorService.findByEmail(Mockito.anyString())).thenReturn(restaurant.getAdministrator());
        Mockito.when(addressService.save(Mockito.any(Address.class))).thenReturn(restaurant.getAddress());

        for (Zone zone : restaurant.getDeliveryZones()) {
            Mockito.when(zoneService.findByName(zone.getName())).thenReturn(zone);
        }

        Mockito.when(restaurantRepository.save(Mockito.any(Restaurant.class))).then(returnsFirstArg());

        Assertions.assertDoesNotThrow(() -> restaurantService.save(RestaurantMapper.getInstance().convertToDTO(restaurant)));
    }

    @Test
    public void testSave_DuplicateRestaurantName() {
        Restaurant restaurant = dummy.createTestRestaurant();

        Mockito.when(restaurantRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(restaurant));

        Assertions.assertThrows(DuplicateRestaurantNameException.class, () -> restaurantService.save(RestaurantMapper.getInstance().convertToDTO(restaurant)));
    }

    @Test
    public void testSave_NoAdministrator() {
        Restaurant restaurant = dummy.createTestRestaurant();

        Mockito.when(restaurantRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(administratorService.findByEmail(Mockito.anyString())).thenReturn(null);

        Assertions.assertThrows(NoSuchAccountException.class, () -> restaurantService.save(RestaurantMapper.getInstance().convertToDTO(restaurant)));
    }

}

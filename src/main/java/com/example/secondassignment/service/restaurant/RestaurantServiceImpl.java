package com.example.secondassignment.service.restaurant;

import com.example.secondassignment.DTO.RestaurantDTO;
import com.example.secondassignment.mappers.RestaurantMapper;
import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.repository.AdministratorRepository;
import com.example.secondassignment.repository.RestaurantRepository;
import com.example.secondassignment.service.address.AddressService;
import com.example.secondassignment.service.administrator.AdministratorService;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.validators.AddressValidator;
import com.example.secondassignment.service.validators.NameValidator;
import com.example.secondassignment.service.validators.UserEmailValidator;
import com.example.secondassignment.service.validators.UserPasswordValidator;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AdministratorRepository administratorRepository;

    public String validateRestaurant(String name) {
        try {
            new NameValidator().validate(name);
            return null;
        } catch (InvalidDataException e) {
            return e.getMessage();
        }
    }

    @Override
    public RestaurantDTO findByName(String name) {
        Optional<Restaurant> restaurant = restaurantRepository.findByName(name);
        return restaurant.map(value -> RestaurantMapper.getInstance().convertToDTO(value)).orElse(null);
    }

    @Override
    public RestaurantDTO save(RestaurantDTO restaurantDTO) throws DuplicateName, InvalidDataException {
        if (this.findByName(restaurantDTO.getName()) != null) {
            throw new DuplicateName("A restaurant with the name " + restaurantDTO.getName() + " already exists!");
        }
        Optional<Administrator> administrator = administratorRepository.findByEmail(restaurantDTO.getAdministrator().getEmail());
        if (!administrator.isPresent()) {
            throw new NoSuchElementException("No corresponding administrator was found!");
        }
        Address address = addressService.save(restaurantDTO.getAddress());

        String validationMsg = validateRestaurant(restaurantDTO.getName());
        if (validationMsg != null) {
            throw new InvalidDataException("The details of the restaurant are incorrect!");
        }

        restaurantDTO.setAddress(address);
        restaurantDTO.setAdministrator(administrator.get());

        Restaurant restaurant = restaurantRepository.save(RestaurantMapper.getInstance().convertFromDTO(restaurantDTO));
        return RestaurantMapper.getInstance().convertToDTO(restaurant);
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

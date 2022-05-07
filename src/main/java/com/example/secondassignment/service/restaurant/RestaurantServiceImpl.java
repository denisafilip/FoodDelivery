package com.example.secondassignment.service.restaurant;

import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.DTO.ZoneDTO;
import com.example.secondassignment.model.mappers.RestaurantMapper;
import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.repository.RestaurantRepository;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import com.example.secondassignment.service.address.AddressService;
import com.example.secondassignment.service.account.administrator.AdministratorServiceImpl;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import com.example.secondassignment.service.validators.NameValidator;
import com.example.secondassignment.service.address.zone.ZoneServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
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

    private final static Logger logger = LogManager.getLogger(RestaurantServiceImpl.class.getName());

    /**
     * Validates the name of a restaurant, before its creation.
     * @param name of the restaurant
     * @return null if the name is valid, an error message if it is not
     */
    public String validateRestaurant(String name) {
        try {
            new NameValidator().validate(name);
            return null;
        } catch (InvalidDataException e) {
            return e.getMessage();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Restaurant findByName(String name) {
        Optional<Restaurant> restaurant = restaurantRepository.findByName(name);
        return restaurant.orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestaurantDTO save(RestaurantDTO restaurantDTO) throws InvalidDataException, DuplicateRestaurantNameException, NoSuchAccountException {
        if (this.findByName(restaurantDTO.getName()) != null) {
            throw new DuplicateRestaurantNameException("A restaurant with the name " + restaurantDTO.getName() + " already exists!");
        }

        Administrator administrator = administratorService.findByEmail(restaurantDTO.getAdministratorDTO().getEmail());
        if (administrator == null) {
            throw new NoSuchAccountException("No corresponding administrator was found!");
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
            throw new InvalidDataException(validationMsg);
        }

        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDTO.getName())
                .address(address)
                .administrator(administrator)
                .deliveryZones(zones)
                .build();
        logger.info("Added restaurant {}", restaurant);
        System.out.println(restaurant);

        return RestaurantMapper.getInstance().convertToDTO(restaurantRepository.save(restaurant));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RestaurantDTO> findAll() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete(Integer id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if(restaurant.isPresent()) {
            restaurantRepository.deleteById(id);
            return "Restaurant " + restaurant.get().getName() + " was deleted successfully!";
        }
        else return "There was no restaurant to delete!";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<InputStreamResource> exportMenuToPDF(String restaurantName) throws FileNotFoundException {
        Restaurant restaurant = this.findByName(restaurantName);
        PDFGenerator generator = new PDFGenerator();
        generator.setRestaurant(restaurant);
        String path = generator.writeMenuToPDF();
        File file = new File(path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentLength(file.length());
        ContentDisposition disposition = ContentDisposition.attachment().filename(file.getName()).build();
        headers.setContentDisposition(disposition);
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}

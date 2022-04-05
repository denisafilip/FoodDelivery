package com.example.secondassignment.controller;

import com.example.secondassignment.DTO.AdministratorDTO;
import com.example.secondassignment.DTO.RestaurantDTO;
import com.example.secondassignment.mappers.AdministratorMapper;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.service.administrator.AdministratorServiceImpl;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import com.example.secondassignment.service.zone.ZoneServiceImpl;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdministratorServiceImpl administratorService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private ZoneServiceImpl zoneService;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<AdministratorDTO> getAdministrators() {
        return administratorService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<AdministratorDTO> save(@Validated @RequestBody Administrator administrator) {
        try {
            return new ResponseEntity<>(AdministratorMapper.getInstance().convertToDTO(administratorService.save(administrator)),
                    HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*@PostMapping("/")
    public ResponseEntity<AdministratorDTO> loginAdministrator(@Validated @RequestBody LoginDTO loginDTO) {
        try {
            return new ResponseEntity<>(administratorService.logIn(loginDTO), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }*/

    @GetMapping("/addRestaurant")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Zone>> register() {
        try {
            return new ResponseEntity<>(zoneService.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //restaurant operations
    @PostMapping("/addRestaurant")
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody(required = false)  RestaurantDTO restaurantDTO) throws InvalidDataException, DuplicateRestaurantNameException {
        System.out.println(restaurantDTO);
        return new ResponseEntity<>(restaurantService.save(restaurantDTO), HttpStatus.CREATED);
       /* try {
            return new ResponseEntity<>(restaurantService.save(restaurantDTO), HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }*/
    }

    @DeleteMapping(value = "/deleteRestaurant")
    public ResponseEntity<String> deleteRestaurant(@RequestBody Integer id) {
        try {
            String msg = restaurantService.delete(id);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //food operations

    //order operations

}

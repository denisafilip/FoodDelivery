package com.example.secondassignment.controller;

import com.example.secondassignment.model.DTO.AdministratorDTO;
import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.DTO.OrderDTO;
import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.model.mappers.AdministratorMapper;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Category;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.model.mappers.RestaurantMapper;
import com.example.secondassignment.service.account.administrator.AdministratorServiceImpl;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.PDFGenerator;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateFoodNameException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import com.example.secondassignment.service.restaurant.food.FoodServiceImpl;
import com.example.secondassignment.service.restaurant.food.category.FoodCategoryServiceImpl;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import com.example.secondassignment.service.address.zone.ZoneServiceImpl;
import com.example.secondassignment.service.restaurant.order.OrderServiceImpl;
import com.example.secondassignment.service.restaurant.order.ViewOrdersFacade;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private FoodCategoryServiceImpl foodCategoryService;

    @Autowired
    private FoodServiceImpl foodService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ViewOrdersFacade viewOrdersFacade;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<AdministratorDTO> getAdministrators() {
        return administratorService.findAll();
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AdministratorDTO getCurrentAdministrator(@Param("adminEmail") String adminEmail) {
        return AdministratorMapper.getInstance().convertToDTO(administratorService.findByEmail(adminEmail));
    }

    @PostMapping("/save")
    public ResponseEntity<AdministratorDTO> save(@Validated @RequestBody Administrator administrator) {
        return new ResponseEntity<>(AdministratorMapper.getInstance().convertToDTO(administratorService.save(administrator)),
                    HttpStatus.CREATED);
    }

    //restaurant operations
    @GetMapping("/addRestaurant")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Zone>> getZones() {
        try {
            return new ResponseEntity<>(zoneService.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody(required = false) RestaurantDTO restaurantDTO) throws
            InvalidDataException, DuplicateRestaurantNameException, NoSuchAccountException {
        return new ResponseEntity<>(restaurantService.save(restaurantDTO), HttpStatus.CREATED);
    }

    //food operations
    @GetMapping("/addFood")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Category>> getFoodCategories() {
        return new ResponseEntity<>(foodCategoryService.findAll(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/addFood")
    public ResponseEntity<FoodDTO> addFood(@RequestBody(required = false) FoodDTO foodDTO) throws InvalidDataException,
            DuplicateFoodNameException {
        return new ResponseEntity<>(foodService.save(foodDTO), HttpStatus.CREATED);
    }

    @GetMapping("/viewMenu")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<FoodDTO>> getFoods(@Param("restaurantName") String restaurantName) throws NoSuchRestaurantException {
        return new ResponseEntity<>(foodService.findByRestaurant(restaurantName), HttpStatus.ACCEPTED);
    }

    //order operations
    @GetMapping("/viewOrders")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<OrderDTO>> getOrders(@Param("restaurantName") String restaurantName)
            throws NoSuchRestaurantException, InvalidDataException {
        return new ResponseEntity<>(viewOrdersFacade.findAllByRestaurantOrCustomer(null, restaurantName), HttpStatus.ACCEPTED);
    }

    @PostMapping("/order/accept")
    public ResponseEntity<OrderDTO> acceptOrder(@RequestBody(required = false) OrderDTO orderDTO) throws InvalidDataException {
        return new ResponseEntity<>(orderService.acceptOrder(orderDTO), HttpStatus.CREATED);
    }

    @PostMapping("/order/decline")
    public ResponseEntity<OrderDTO> declineOrder(@RequestBody(required = false) OrderDTO orderDTO) throws InvalidDataException {
        return new ResponseEntity<>(orderService.declineOrder(orderDTO), HttpStatus.CREATED);
    }

    @PostMapping("/order/startDelivery")
    public ResponseEntity<OrderDTO> startDelivery(@RequestBody(required = false) OrderDTO orderDTO) throws InvalidDataException {
        return new ResponseEntity<>(orderService.startDelivery(orderDTO), HttpStatus.CREATED);
    }

    @PostMapping("/order/endDelivery")
    public ResponseEntity<OrderDTO> endDelivery(@RequestBody(required = false) OrderDTO orderDTO) throws InvalidDataException {
        return new ResponseEntity<>(orderService.endDelivery(orderDTO), HttpStatus.CREATED);
    }

    @RequestMapping("viewMenu/export")
    public ResponseEntity<InputStreamResource> generatePDF(@Param("restaurantName") String restaurantName) throws IOException {
        Restaurant restaurant = restaurantService.findByName(restaurantName);
        PDFGenerator generator = new PDFGenerator();
        generator.setRestaurant(restaurant);
        String path = generator.writeMenuToPDF();
        File file = new File(path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        System.out.println(file.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentLength(file.length());
        ContentDisposition disposition = ContentDisposition.attachment().filename(file.getName()).build();
        headers.setContentDisposition(disposition);
        return ResponseEntity.ok().headers(headers).body(resource);
    }

}

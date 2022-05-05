package com.example.secondassignment.controller;

import com.example.secondassignment.model.DTO.AdministratorDTO;
import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.DTO.OrderDTO;
import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.mappers.AdministratorMapper;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Category;
import com.example.secondassignment.model.Zone;
import com.example.secondassignment.service.account.administrator.AdministratorServiceImpl;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateFoodNameException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import com.example.secondassignment.service.restaurant.food.FoodServiceImpl;
import com.example.secondassignment.service.restaurant.food.category.FoodCategoryServiceImpl;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import com.example.secondassignment.service.address.zone.ZoneServiceImpl;
import com.example.secondassignment.service.restaurant.order.OrderServiceImpl;
import com.example.secondassignment.service.restaurant.order.ViewOrdersFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Controller that handles the operations of an administrator.
 */
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
            DuplicateFoodNameException, NoSuchRestaurantException {
        return new ResponseEntity<>(foodService.save(foodDTO), HttpStatus.CREATED);
    }

    @GetMapping("/viewMenu")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<FoodDTO>> getFoods(@Param("restaurantName") String restaurantName) throws NoSuchRestaurantException {
        return new ResponseEntity<>(foodService.findByRestaurant(restaurantName), HttpStatus.ACCEPTED);
    }

    //order operations

    /**
     * Gets all orders of a restaurant.
     * @param restaurantName of the restaurant
     * @return response received after the retrieval of the orders from the database
     * @throws NoSuchRestaurantException If no restaurant with the given name is found.
     * @throws InvalidDataException If the name of the restaurant is invalid
     */
    @GetMapping("/viewOrders")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<OrderDTO>> getOrders(@Param("restaurantName") String restaurantName)
            throws NoSuchRestaurantException, InvalidDataException {
        return new ResponseEntity<>(viewOrdersFacade.findAllByRestaurantOrCustomer(null, restaurantName), HttpStatus.ACCEPTED);
    }

    /**
     * Marks the acceptance of an order
     * @param orderDTO - details of the order that is accepted
     * @return - response received after the status change of the delivery
     * @throws InvalidDataException if the status change is invalid
     */
    @PostMapping("/order/accept")
    public ResponseEntity<OrderDTO> acceptOrder(@RequestBody(required = false) OrderDTO orderDTO) throws InvalidDataException {
        return new ResponseEntity<>(orderService.acceptOrder(orderDTO), HttpStatus.CREATED);
    }

    /**
     * Marks the refusal of an order delivery
     * @param orderDTO - details of the order that is declined
     * @return - response received after the status change of the order
     * @throws InvalidDataException if the status change is invalid
     */
    @PostMapping("/order/decline")
    public ResponseEntity<OrderDTO> declineOrder(@RequestBody(required = false) OrderDTO orderDTO) throws InvalidDataException {
        return new ResponseEntity<>(orderService.declineOrder(orderDTO), HttpStatus.CREATED);
    }

    /**
     * Marks the start of a delivery
     * @param orderDTO - details of the order whose delivery is started
     * @return - response received after the status change of the delivery
     * @throws InvalidDataException if the status change is invalid
     */
    @PostMapping("/order/startDelivery")
    public ResponseEntity<OrderDTO> startDelivery(@RequestBody(required = false) OrderDTO orderDTO) throws InvalidDataException {
        return new ResponseEntity<>(orderService.startDelivery(orderDTO), HttpStatus.CREATED);
    }

    /**
     * Marks the end of a delivery
     * @param orderDTO - details of the order whose delivery is finished
     * @return - response received after the status change of the delivery
     * @throws InvalidDataException if the status change is invalid
     */
    @PostMapping("/order/endDelivery")
    public ResponseEntity<OrderDTO> endDelivery(@RequestBody(required = false) OrderDTO orderDTO) throws InvalidDataException {
        return new ResponseEntity<>(orderService.endDelivery(orderDTO), HttpStatus.CREATED);
    }

    /**
     * Exports the menu of a restaurant to a PDF file
     * @param restaurantName of the restaurant whose menu is exported
     * @return response received after exporting the menu
     * @throws IOException if the PDF file is not found
     */
    @RequestMapping("viewMenu/export")
    public ResponseEntity<InputStreamResource> generatePDF(@Param("restaurantName") String restaurantName) throws IOException {
        return restaurantService.exportMenuToPDF(restaurantName);
    }

}

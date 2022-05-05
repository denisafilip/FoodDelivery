package com.example.secondassignment.service.restaurant.order;

import com.example.secondassignment.model.DTO.CustomerDTO;
import com.example.secondassignment.model.DTO.OrderDTO;
import com.example.secondassignment.model.DTO.RestaurantDTO;
import com.example.secondassignment.model.OrderStatus;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateRestaurantNameException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Interface for the food order of a customer
 */
public interface OrderService {

    /**
     * Saves a new order instance to the database.
     * @param orderDTO contains the details of the new order to be saved
     * @return The saved order DTO.
     * @throws InvalidDataException If the details of the order are invalid/
     */
    @Transactional
    OrderDTO save(OrderDTO orderDTO) throws InvalidDataException;

    /**
     * Retrieves all existing orders from the database.
     * @return A list containing all orders.
     */
    List<OrderDTO> findAll();

    /**
     * Retrieves all existing orders with a specific status.
     * @param status by which the orders are filtered
     * @return A list containing all orders with a specific status.
     */
    List<OrderDTO> findAllByStatus(OrderStatus status);

    /**
     * Retrieves all existing orders placed by a specific customer.
     * @param customerDTO by which the orders are filtered
     * @return A list containing all orders placed by a specific customer.
     */
    List<OrderDTO> findAllByCustomer(CustomerDTO customerDTO);

    /**
     * Retrieves all existing orders placed for a specific restaurant
     * @param restaurantDTO by which the orders are filtered
     * @return A list containing all orders placed for a specific restaurant.
     */
    List<OrderDTO> findAllByRestaurant(RestaurantDTO restaurantDTO);

    /**
     * Retrieves all existing orders placed for a specific restaurant and having a specific status
     * @param restaurantDTO by which the orders are filtered
     * @param status by which the orders are filtered
     * @return A list containing all orders placed for a specific restaurant and having a specific status
     */
    List<OrderDTO> findAllByRestaurantAndStatus(RestaurantDTO restaurantDTO, OrderStatus status);

    /**
     * Retrieves all existing orders placed for a specific restaurant
     * @param restaurantName of the restaurant by which the orders are filtered
     * @return A list containing all orders placed for a specific restaurant.
     * @throws NoSuchRestaurantException If the name of the restaurant does not match any existing restaurant.
     */
    List<OrderDTO> findAllByRestaurantName(String restaurantName) throws NoSuchRestaurantException;

    /**
     * Retrieves all existing orders placed by a specific customer.
     * @param customerEmail of the customer
     * @return A list containing all orders placed by a specific customer.
     * @throws NoSuchAccountException If the email of the customer does not match any existing account.
     */
    List<OrderDTO> findAllByCustomerEmail(String customerEmail) throws NoSuchAccountException;

    /**
     * Updates the status of an existing order.
     * @param orderStatus to which the status of the order will be changed
     * @param orderDTO whose status will be transitioned
     * @return The updated order.
     * @throws InvalidDataException If the order cannot be transitioned to the given order status.
     */
    OrderDTO updateOrderStatus(OrderStatus orderStatus, OrderDTO orderDTO) throws InvalidDataException;

    /**
     * Changes the status of an order to ACCEPTED.
     * @param orderDTO whose status will be transitioned
     * @return The updated order.
     * @throws InvalidDataException If the order cannot be transitioned to the given order status.
     */
    OrderDTO acceptOrder(OrderDTO orderDTO) throws InvalidDataException;

    /**
     * Changes the status of an order to DECLINED.
     * @param orderDTO whose status will be transitioned
     * @return The updated order.
     * @throws InvalidDataException If the order cannot be transitioned to the given order status.
     */
    OrderDTO declineOrder(OrderDTO orderDTO) throws InvalidDataException;

    /**
     * Changes the status of an order to IN_DELIVERY.
     * @param orderDTO whose status will be transitioned
     * @return The updated order.
     * @throws InvalidDataException If the order cannot be transitioned to the given order status.
     */
    OrderDTO startDelivery(OrderDTO orderDTO) throws InvalidDataException;

    /**
     * Changes the status of an order to DELIVERED.
     * @param orderDTO whose status will be transitioned
     * @return The updated order.
     * @throws InvalidDataException If the order cannot be transitioned to the given order status.
     */
    OrderDTO endDelivery(OrderDTO orderDTO) throws InvalidDataException;
}

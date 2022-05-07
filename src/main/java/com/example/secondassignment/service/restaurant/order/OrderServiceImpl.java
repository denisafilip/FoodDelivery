package com.example.secondassignment.service.restaurant.order;

import com.example.secondassignment.model.*;
import com.example.secondassignment.model.DTO.*;
import com.example.secondassignment.model.mappers.OrderMapper;
import com.example.secondassignment.repository.OrderRepository;
import com.example.secondassignment.service.account.customer.CustomerServiceImpl;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import com.example.secondassignment.service.restaurant.food.FoodServiceImpl;
import com.example.secondassignment.service.restaurant.order.status.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class.getName());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private FoodServiceImpl foodService;

    @Autowired
    private EmailService emailService;

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO save(OrderDTO orderDTO) throws InvalidDataException {
        logger.info("Create order {}", orderDTO);
        Customer customer = customerService.findByEmail(orderDTO.getCustomer().getEmail());

        if (customer == null) {
            logger.error("No suitable customer was found when placing the order.");
            throw new InvalidDataException("No suitable customer was found when placing the order.");
        }

        Restaurant restaurant = restaurantService.findByName(orderDTO.getRestaurant().getName());

        if (restaurant == null) {
            logger.error("No suitable restaurant was found when placing the order.");
            throw new InvalidDataException("No suitable restaurant was found when placing the order.");
        }

        if (!restaurant.getDeliveryZones().contains(customer.getAddress().getZone())) {
            logger.error("The restaurant {} does not deliver food to the zone {}",
                    restaurant.getName(),
                    customer.getAddress().getZone().getName());
            throw new InvalidDataException("Sorry! The restaurant " + restaurant.getName() + " does not deliver food to your address!");
        }

        List<Food> foods = new ArrayList<>();
        for (FoodDTO foodDTO : orderDTO.getFoods()) {
            Food food = foodService.findByNameAndRestaurant(foodDTO.getName(), restaurant);
            if (food != null) {
                foods.add(food);
            }
        }

        Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .foods(foods)
                .date(LocalDate.now())
                .status(OrderStatus.PENDING)
                .total(foods.stream().mapToInt(Food::getPrice).sum())
                .build();
        Order savedOrder = orderRepository.save(order);
        emailService.sendOrderEmail(savedOrder);
        return OrderMapper.getInstance().convertToDTO(savedOrder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> findAllByStatus(OrderStatus status) {
        Optional<List<Order>> orders = orderRepository.findAllByStatus(status);

        return orders.map(ordersList -> ordersList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> findAllByCustomer(CustomerDTO customerDTO) {
        Customer customer = customerService.findByEmail(customerDTO.getEmail());

        Optional<List<Order>> orders = orderRepository.findAllByCustomer(customer);

        return orders.map(ordersList -> ordersList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> findAllByRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantService.findByName(restaurantDTO.getName());

        Optional<List<Order>> orders = orderRepository.findAllByRestaurant(restaurant);

        return orders.map(ordersList -> ordersList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> findAllByRestaurantAndStatus(RestaurantDTO restaurantDTO, OrderStatus status) {
        Restaurant restaurant = restaurantService.findByName(restaurantDTO.getName());

        Optional<List<Order>> orders = orderRepository.findAllByRestaurantAndStatus(restaurant, status);

        return orders.map(ordersList -> ordersList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> findAllByRestaurantName(String restaurantName) throws NoSuchRestaurantException {
        Restaurant restaurant = restaurantService.findByName(restaurantName);

        if (restaurant == null) {
            throw new NoSuchRestaurantException("No restaurant with the name " + restaurantName + " exists.");
        }

        Optional<List<Order>> orders = orderRepository.findAllByRestaurant(restaurant);

        return orders.map(orderList -> orderList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> findAllByCustomerEmail(String customerEmail) throws NoSuchAccountException {
        Customer customer = customerService.findByEmail(customerEmail);

        if (customer == null) {
            throw new NoSuchAccountException("No customer with the email " + customerEmail + " exists.");
        }

        Optional<List<Order>> orders = orderRepository.findAllByCustomer(customer);

        return orders.map(orderList -> orderList.stream()
                .map(OrderMapper.getInstance()::convertToDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO updateOrderStatus(OrderStatus orderStatus, OrderDTO orderDTO) throws InvalidDataException {
        OrderState orderState;

        switch (orderDTO.getStatus()) {
            case PENDING: orderState = new PendingOrderState(); break;
            case ACCEPTED: orderState = new AcceptedOrderState(); break;
            case IN_DELIVERY: orderState = new InDeliveryOrderState(); break;
            case DELIVERED: orderState = new DeliveredOrderState(); break;
            case DECLINED: orderState = new DeclinedOrderState(); break;
            default:
                throw new IllegalStateException("Unexpected value: " + orderDTO.getStatus());
        }

        Order order = orderRepository.getById(orderDTO.getIdOrder());

        orderState.changeState(order, orderStatus);

        try {
            Order savedOrder = orderRepository.save(order);
            return OrderMapper.getInstance().convertToDTO(savedOrder);
        } catch (Exception e) {
            throw new InvalidDataException("Could not update the order!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO acceptOrder(OrderDTO orderDTO) throws InvalidDataException {
        return updateOrderStatus(OrderStatus.ACCEPTED, orderDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO declineOrder(OrderDTO orderDTO) throws InvalidDataException {
        return updateOrderStatus(OrderStatus.DECLINED, orderDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO startDelivery(OrderDTO orderDTO) throws InvalidDataException {
        return updateOrderStatus(OrderStatus.IN_DELIVERY, orderDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO endDelivery(OrderDTO orderDTO) throws InvalidDataException {
        return updateOrderStatus(OrderStatus.DELIVERED, orderDTO);
    }
}

package com.example.secondassignment;

import com.example.secondassignment.model.*;
import com.example.secondassignment.model.mappers.OrderMapper;
import com.example.secondassignment.repository.OrderRepository;
import com.example.secondassignment.service.account.customer.CustomerServiceImpl;
import com.example.secondassignment.service.exceptions.InvalidDataException;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.food.FoodServiceImpl;
import com.example.secondassignment.service.restaurant.order.OrderServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private RestaurantServiceImpl restaurantService;

    @Mock
    private FoodServiceImpl foodService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private final DummyTestObjects dummy = new DummyTestObjects();

    @Test
    public void testSave_Success() {
        Order order = dummy.createTestOrder();
        order.getRestaurant().getDeliveryZones().add(order.getCustomer().getAddress().getZone());

        Mockito.when(customerService.findByEmail(Mockito.anyString())).thenReturn(order.getCustomer());
        Mockito.when(restaurantService.findByName(Mockito.anyString())).thenReturn(order.getRestaurant());

        for (Food food : order.getFoods()) {
            Mockito.when(foodService.findByNameAndRestaurant(Mockito.anyString(), Mockito.any(Restaurant.class)))
                    .thenReturn(food);
        }

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).then(returnsFirstArg());

        Assertions.assertDoesNotThrow(() -> orderService.save(OrderMapper.getInstance().convertToDTO(order)));
    }

    @Test
    public void testSave_NoRestaurant() {
        Order order = dummy.createTestOrder();
        Mockito.when(customerService.findByEmail(Mockito.anyString())).thenReturn(null);

        Mockito.when(customerService.findByEmail(Mockito.anyString())).thenReturn(order.getCustomer());
        Mockito.when(restaurantService.findByName(Mockito.anyString())).thenReturn(null);

        Assertions.assertThrows(InvalidDataException.class, () -> orderService.save(OrderMapper.getInstance().convertToDTO(order)));
    }

    @Test
    public void testSave_DoesNotDeliverToSpecificZone() {
        Order order = dummy.createTestOrder();
        Mockito.when(customerService.findByEmail(Mockito.anyString())).thenReturn(null);

        Mockito.when(customerService.findByEmail(Mockito.anyString())).thenReturn(order.getCustomer());
        Mockito.when(restaurantService.findByName(Mockito.anyString())).thenReturn(order.getRestaurant());

        Assertions.assertThrows(InvalidDataException.class, () -> orderService.save(OrderMapper.getInstance().convertToDTO(order)));
    }

    @Test
    public void testUpdateOrderStatus() {
        Order order = dummy.createTestOrder();

        Mockito.when(orderRepository.getById(Mockito.anyInt())).thenReturn(order);

        Mockito.when(customerService.findByEmail(Mockito.anyString())).thenReturn(order.getCustomer());
        Mockito.when(restaurantService.findByName(Mockito.anyString())).thenReturn(order.getRestaurant());

        for (Food food : order.getFoods()) {
            Mockito.when(foodService.findByNameAndRestaurant(Mockito.anyString(), Mockito.any(Restaurant.class)))
                    .thenReturn(food);
        }

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).then(returnsFirstArg());

        Assertions.assertDoesNotThrow(() -> orderService.updateOrderStatus(OrderStatus.ACCEPTED, OrderMapper.getInstance().convertToDTO(order)));
    }
}

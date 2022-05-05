package com.example.secondassignment;

import com.example.secondassignment.model.DTO.FoodDTO;
import com.example.secondassignment.model.Food;
import com.example.secondassignment.model.Restaurant;
import com.example.secondassignment.repository.FoodRepository;
import com.example.secondassignment.service.restaurant.RestaurantServiceImpl;
import com.example.secondassignment.service.restaurant.exceptions.DuplicateFoodNameException;
import com.example.secondassignment.service.restaurant.exceptions.NoSuchRestaurantException;
import com.example.secondassignment.service.restaurant.food.FoodServiceImpl;
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
public class FoodServiceTest {
    @Mock
    private FoodRepository foodRepository;

    @Mock
    private RestaurantServiceImpl restaurantService;

    @InjectMocks
    private FoodServiceImpl foodService;

    private final DummyTestObjects dummy = new DummyTestObjects();

    @Test
    public void testFindByNameAndRestaurant_Success() {
        Food food = dummy.createTestFood();

        Mockito.when(foodRepository.findByNameAndRestaurant(food.getName(), food.getRestaurant())).thenReturn(Optional.of(food));

        Food foundFood = foodService.findByNameAndRestaurant(food.getName(), food.getRestaurant());
        Assertions.assertEquals(food, foundFood);
    }

    @Test
    public void testFindByNameAndRestaurant_NotInDatabase() {
        Food food = dummy.createTestFood();

        Mockito.when(foodRepository.findByNameAndRestaurant(food.getName(), food.getRestaurant())).thenReturn(Optional.empty());

        Food foundFood = foodService.findByNameAndRestaurant(food.getName(), food.getRestaurant());
        Assertions.assertNull(foundFood);
    }

   @Test
    public void testSave_Success() {
        Food food = dummy.createTestFood();
        FoodDTO foodDTO = dummy.createTestFoodDTO();

        Mockito.when(restaurantService.findByName(Mockito.anyString())).thenReturn(food.getRestaurant());
        Mockito.when(foodRepository.findByNameAndRestaurant(Mockito.anyString(), Mockito.any(Restaurant.class)))
                .thenReturn(Optional.empty());

       Mockito.when(foodRepository.save(Mockito.any(Food.class))).then(returnsFirstArg());

        Assertions.assertDoesNotThrow(() -> foodService.save(foodDTO));
   }

    @Test
    public void testSave_NoRestaurant() {
        FoodDTO foodDTO = dummy.createTestFoodDTO();

        Mockito.when(restaurantService.findByName(Mockito.anyString())).thenReturn(null);

        Assertions.assertThrows(NoSuchRestaurantException.class, () -> foodService.save(foodDTO));
    }

    @Test
    public void testSave_DuplicateFood() {
        Food food = dummy.createTestFood();
        FoodDTO foodDTO = dummy.createTestFoodDTO();

        Mockito.when(restaurantService.findByName(Mockito.anyString())).thenReturn(food.getRestaurant());
        Mockito.when(foodRepository.findByNameAndRestaurant(Mockito.anyString(), Mockito.any(Restaurant.class)))
                .thenReturn(Optional.of(food));

        Assertions.assertThrows(DuplicateFoodNameException.class, () -> foodService.save(foodDTO));
    }

}

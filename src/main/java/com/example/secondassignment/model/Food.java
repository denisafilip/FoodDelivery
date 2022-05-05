package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Class that holds the necessary details of a food present on the menu of a restaurant.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "food")
public class Food {

    /**
     * Identifier of a food in the database table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_food", updatable = false, unique = true, nullable = false)
    private Integer idFood;

    /**
     * Name of a food.
     */
    @NonNull
    @Column(name = "name", length = 100)
    private String name;

    /**
     * Price of a food, in RON.
     */
    @NonNull
    @Column(name = "price")
    private Integer price;

    /**
     * Details of a food, such as ingredients or allergens.
     */
    @NonNull
    @Column(name = "description")
    private String description;

    /**
     * Food category of a food.
     */
    @NonNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_category")
    private Category category;

    /**
     * Restaurant to which the food belongs.
     */
    @NonNull
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    /**
     * History of the orders in which the food was bought.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "foods")
    private List<Order> orders;

    @Override
    public String toString() {
        return "Food item " + idFood +
                ": " + name + "\n   description: " + description +
                "\n   price: " + price +
                "\n   category: " + category.getName();
    }
}

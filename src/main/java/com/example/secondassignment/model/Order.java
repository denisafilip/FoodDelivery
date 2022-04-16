package com.example.secondassignment.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Class that represents the order placed by a customer to a restaurant.
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "delivery_order")
public class Order {

    /**
     * Identifier of the order in the database table.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_order", updatable = false, unique = true, nullable = false)
    private Integer idOrder;

    /**
     * Date on which the order was placed by the customer.
     */
    @NonNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    /**
     * Customer that placed the order.
     */
    @NonNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_customer")
    private Customer customer;

    /**
     * Restaurant for which the order was placed.
     */
    @NonNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    /**
     * Current status of the order.
     */
    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    /**
     * Total price of the order.
     */
    @NonNull
    @Column(name = "total", nullable = false)
    private Integer total;

    /**
     * The foods which were ordered from the restaurant.
     */
    @NonNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "food_order",
            joinColumns = @JoinColumn(name = "id_order"),
            inverseJoinColumns = @JoinColumn(name = "id_food"))
    private List<Food> foods;
}

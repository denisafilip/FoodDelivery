package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Class representing a restaurant that is registered in the food delivery application.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "restaurant")
public class Restaurant {

    /**
     * Identifier of the restaurant in the database table.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_restaurant", updatable = false, unique = true, nullable = false)
    private Integer idRestaurant;

    /**
     * Name of the restaurant.
     */
    @NonNull
    @Column(name = "name", length = 100, unique = true)
    private String name;

    /**
     * Address at which the restaurant is located.
     */
    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_address")
    private Address address;

    /**
     * Zones to which the restaurant delivers food.
     */
    @NonNull
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "delivery_zone",
            joinColumns = @JoinColumn(name = "id_restaurant"),
            inverseJoinColumns = @JoinColumn(name = "id_zone"))
    private Set<Zone> deliveryZones;

    /**
     * Administrator of the restaurant.
     */
    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_administrator")
    @JsonIgnore
    @ToString.Exclude
    private Administrator administrator;

    /**
     * Menu of the restaurant.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<Food> foods;

    /**
     * History of the orders received by the restaurant.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<Order> orders;
}

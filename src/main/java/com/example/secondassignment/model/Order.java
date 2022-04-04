package com.example.secondassignment.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "delivery_order")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_order", updatable = false, unique = true, nullable = false)
    private Integer idOrder;

    @NonNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_status")
    private OrderStatus orderStatus;

    @NonNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "food_order",
            joinColumns = @JoinColumn(name = "id_food"),
            inverseJoinColumns = @JoinColumn(name = "id_order"))
    private List<Food> foods;



}

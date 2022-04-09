package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_order", updatable = false, unique = true, nullable = false)
    private Integer idOrder;

    @NonNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NonNull
    @JsonManagedReference(value = "order-customer")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @NonNull
    @JsonManagedReference(value = "order-restaurant")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @NonNull
    @Column(name = "total", nullable = false)
    private Integer total;

    @NonNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "food_order",
            joinColumns = @JoinColumn(name = "id_order"),
            inverseJoinColumns = @JoinColumn(name = "id_food"))
    private List<Food> foods;
}

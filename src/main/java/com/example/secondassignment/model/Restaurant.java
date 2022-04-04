package com.example.secondassignment.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_restaurant", updatable = false, unique = true, nullable = false)
    private Integer idRestaurant;

    @NonNull
    @Column(name = "name", length = 100)
    private String name;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private Address address;

    @NonNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "delivery_zone",
            joinColumns = @JoinColumn(name = "id_zone"),
            inverseJoinColumns = @JoinColumn(name = "id_restaurant"))
    private List<Zone> deliveryZones;   // you already have the zones, pick from the from a dropdown menu and they will appear in the delivery zone table, along with restaurant id

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_administrator")
    private Administrator administrator;

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<Order> orders;
}

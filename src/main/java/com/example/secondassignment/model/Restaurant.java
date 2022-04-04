package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "name", length = 100, unique = true)
    private String name;

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_address")
    private Address address;

    @NonNull
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "delivery_zone",
            joinColumns = @JoinColumn(name = "id_zone"),
            inverseJoinColumns = @JoinColumn(name = "id_restaurant"))
    private List<Zone> deliveryZones;

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_administrator")
    @JsonIgnore
    private Administrator administrator;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<Order> orders;
}

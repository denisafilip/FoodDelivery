package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idRestaurant")
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
            joinColumns = @JoinColumn(name = "id_restaurant"),
            inverseJoinColumns = @JoinColumn(name = "id_zone"))
    private Set<Zone> deliveryZones;

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_administrator")
    @JsonIgnore
    @ToString.Exclude
    private Administrator administrator;

    @JsonManagedReference
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<Food> foods;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<Order> orders;
}

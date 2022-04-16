package com.example.secondassignment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Class representing the major zones of a city.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Table(name = "zone")
public class Zone {

    /**
     * Identifier of a zone in the database table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zone", updatable = false, unique = true, nullable = false)
    private Integer idZone;

    /**
     * Name of the zone.
     */
    @NonNull
    @Column(name = "name", unique = true, length = 100)
    private String name;

    /**
     * Addresses which are located in this zone.
     */
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "zone", orphanRemoval = true)
    private List<Address> addresses;

    /**
     * Restaurants which deliver to this zone.
     */
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "deliveryZones")
    private List<Restaurant> restaurants;

}

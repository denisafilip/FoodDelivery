package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Class containing the necessary data for the address of a customer/restaurant.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "address")
public class Address {

    /**
     * Identifier of an address in the database table.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_address", updatable = false, unique = true, nullable = false)
    private Integer idAddress;

    /**
     * Name of the street.
     */
    @NonNull
    @Column(name = "street", length = 100)
    private String street;

    /**
     * Street number.
     */
    @NonNull
    @Column(name = "number")
    private String number;

    /**
     * Zone in a city in which the address is situated.
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_zone")
    private Zone zone;

    /**
     * City of the address.
     */
    @NonNull
    @Column(name = "city")
    private String city;

    /**
     * Country of the address.
     */
    @NonNull
    @Column(name = "country")
    private String country;

    /**
     * Postal Code of the address.
     */
    @NonNull
    @Column(name = "postal_code")
    private String postalCode;

    /**
     * Customer living at this address.
     */
    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    private Customer customer;

    /**
     * Restaurant located at this address.
     */
    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    private Restaurant restaurant;

}

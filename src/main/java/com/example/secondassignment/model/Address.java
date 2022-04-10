package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

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

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_address", updatable = false, unique = true, nullable = false)
    private Integer idAddress;

    @NonNull
    @Column(name = "street", length = 100)
    private String street;

    @NonNull
    @Column(name = "number")
    private String number;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_zone")
    private Zone zone;

    @NonNull
    @Column(name = "city")
    private String city;

    @NonNull
    @Column(name = "country")
    private String country;

    @NonNull
    @Column(name = "postal_code")
    private String postalCode;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    private Customer customer;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    private Restaurant restaurant;

}

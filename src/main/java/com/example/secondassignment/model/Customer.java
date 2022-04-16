package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for the account of a customer, which is a specific type of account.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "customer")
public class Customer extends Account {

    /**
     * Address of the customer, used in determining which restaurants can deliver food to this customer.
     */
    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_address")
    private Address address;

    /**
     * Order history of the customer.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private Set<Order> orders;

    /**
     * Constructor used for creating a customer instance.
     * @param firstName of the customer
     * @param lastName of the customer
     * @param email of the customer
     * @param password of the customer
     * @param address of the customer
     */
    @Builder(builderMethodName = "CustomerBuilder")
    public Customer(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password, @NonNull Address address) {
        super(firstName, lastName, email, password);
        this.address = address;
        this.orders = new HashSet<>();
    }
}

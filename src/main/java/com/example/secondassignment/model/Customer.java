package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "customer")
public class Customer extends Account {

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_address")
    private Address address;

    //@JsonBackReference(value = "order-customer")
    @JsonIgnore
    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private Set<Order> orders;

    @Builder(builderMethodName = "CustomerBuilder")
    public Customer(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password, @NonNull Address address) {
        super(firstName, lastName, email, password);
        this.address = address;
        this.orders = new HashSet<>();
    }
}

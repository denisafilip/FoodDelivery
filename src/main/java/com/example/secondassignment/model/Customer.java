package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @JsonManagedReference(value = "order-customer")
    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private List<Order> orders;

    @Builder(builderMethodName = "CustomerBuilder")
    public Customer(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password, @NonNull Address address) {
        super(firstName, lastName, email, password);
        this.address = address;
    }
}

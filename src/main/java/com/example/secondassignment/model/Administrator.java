package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "administrator")
public class Administrator extends Account {

    @OneToOne(mappedBy = "administrator", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Restaurant restaurant;

    @Builder(builderMethodName = "AdminBuilder")
    public Administrator(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password) {
        super(firstName, lastName, email, password);
    }
}

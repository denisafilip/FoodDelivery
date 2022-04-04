package com.example.secondassignment.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "administrator")
public class Administrator extends Account {

    /*@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_administrator", updatable = false, unique = true, nullable = false)
    private Integer idAdministrator;*/

    @OneToOne(mappedBy = "administrator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Restaurant restaurant;

    @Builder(builderMethodName = "AdminBuilder")
    public Administrator(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password) {
        super(firstName, lastName, email, password);
    }
}

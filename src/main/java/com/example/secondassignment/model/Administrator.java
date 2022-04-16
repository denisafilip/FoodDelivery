package com.example.secondassignment.model;

import lombok.*;

import javax.persistence.*;

/**
 * Class for the account of the administrator of a restaurant, a specific type of account.
 */
@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "administrator")
public class Administrator extends Account {

    /**
     * Restaurant that is owned by the administrator.
     */
    @OneToOne(mappedBy = "administrator", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Restaurant restaurant;

    /**
     * Constructor which builds an administrator instance.
     * @param firstName of the administrator
     * @param lastName of the administrator
     * @param email of the administrator
     * @param password of the administrator
     */
    @Builder(builderMethodName = "AdminBuilder")
    public Administrator(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password) {
        super(firstName, lastName, email, password);
    }
}

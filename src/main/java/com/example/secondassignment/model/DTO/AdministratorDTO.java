package com.example.secondassignment.model.DTO;

import com.example.secondassignment.model.Restaurant;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class AdministratorDTO extends AccountDTO {
    private Restaurant restaurant;

    @Builder(builderMethodName = "AdministratorDTOBuilder")
    public AdministratorDTO(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password, Restaurant restaurant) {
        super(firstName, lastName, email, password);
        this.restaurant = restaurant;
    }
}

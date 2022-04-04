package com.example.secondassignment.DTO;

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
    public AdministratorDTO(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password) {
        super(firstName, lastName, email, password);
    }
}

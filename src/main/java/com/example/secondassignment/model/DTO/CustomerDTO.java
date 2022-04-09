package com.example.secondassignment.model.DTO;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Order;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper=true)
public class CustomerDTO extends AccountDTO {

    @NonNull
    private Address address;

    @Builder(builderMethodName = "CustomerDTOBuilder")
    public CustomerDTO(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password, Address address) {
        super(firstName, lastName, email, password);
        this.address = address;
    }
}

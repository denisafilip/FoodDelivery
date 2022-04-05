package com.example.secondassignment.DTO;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Administrator;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RestaurantDTO {

    @NonNull
    private String name;

    @NonNull
    private Address address;

    @NonNull
    private Set<ZoneDTO> deliveryZones;

    @NonNull
    private AdministratorDTO administratorDTO;
}

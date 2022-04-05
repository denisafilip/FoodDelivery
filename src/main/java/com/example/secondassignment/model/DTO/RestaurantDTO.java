package com.example.secondassignment.model.DTO;

import com.example.secondassignment.model.Address;
import lombok.*;

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

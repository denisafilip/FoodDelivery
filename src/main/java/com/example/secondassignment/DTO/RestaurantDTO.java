package com.example.secondassignment.DTO;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.model.Zone;
import lombok.*;

import java.util.List;

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
    private List<Zone> deliveryZones;

    @NonNull
    private Administrator administrator;
}

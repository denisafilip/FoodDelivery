package com.example.secondassignment.DTO;

import com.example.secondassignment.model.Address;
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

    private String name;
    private Address address;
    private List<Zone> deliveryZones;
}

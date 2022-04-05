package com.example.secondassignment.model.DTO;

import com.example.secondassignment.model.Zone;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AddressDTO {

    private String street;
    private String number;
    private String city;
    private String county;
    private Zone zone;
    private String country;
    private String postalCode;

}

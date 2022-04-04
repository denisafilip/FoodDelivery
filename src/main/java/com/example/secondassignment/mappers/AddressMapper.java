package com.example.secondassignment.mappers;

import com.example.secondassignment.DTO.AddressDTO;
import com.example.secondassignment.model.Address;

public class AddressMapper implements Mapper<Address, AddressDTO> {

    private static AddressMapper addressMapper = null;

    private AddressMapper() {

    }

    public static AddressMapper getInstance() {
        if (addressMapper == null) {
            addressMapper = new AddressMapper();
        }
        return addressMapper;
    }

    @Override
    public Address convertFromDTO(AddressDTO addressDTO) {
        return null;
    }

    @Override
    public AddressDTO convertToDTO(Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .zone(address.getZone())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }
}

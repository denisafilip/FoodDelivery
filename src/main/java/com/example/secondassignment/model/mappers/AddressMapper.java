package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.AddressDTO;
import com.example.secondassignment.model.Address;

/**
 * Singleton class used for mapping an Address to an AddressDTO and vice-versa.
 */
public class AddressMapper implements Mapper<Address, AddressDTO> {

    /**
     * Singleton instance of the AddressMapper class.
     */
    private static AddressMapper addressMapper = null;

    /**
     * Constructor.
     */
    private AddressMapper() {

    }

    /**
     * Retrieves the single instance of the AddressMapper class.
     * @return the instance of the AddressMapper class.
     */
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

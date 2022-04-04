package com.example.secondassignment.service.address;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        Optional<Address> addressOptional = addressRepository.findByStreetAndNumberAndCity(address.getStreet(), address.getNumber(), address.getCity());
        return addressOptional.orElseGet(() -> addressRepository.save(address));
        /*return addressRepository
                .findByStreetAndNumberAndCity(address.getStreet(), address.getNumber(), address.getCity())
                .orElse(addressRepository.save(address));*/
    }
}

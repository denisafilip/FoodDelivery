package com.example.secondassignment.service.address;

import com.example.secondassignment.model.Address;
import com.example.secondassignment.model.Administrator;

import javax.transaction.Transactional;

public interface AddressService {
    @Transactional
    Address save(Address address);
}

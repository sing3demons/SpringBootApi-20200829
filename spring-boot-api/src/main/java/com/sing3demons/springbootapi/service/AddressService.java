package com.sing3demons.springbootapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sing3demons.springbootapi.entity.Address;
import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.repository.AddressRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findByUser(User user) {
        return addressRepository.findByUser(user);
    }

    public Address create(User user, String line1, String line2, String zipcode) {

        // create
        Address entity = new Address();

        entity.setUser(user);
        entity.setLine1(line1);
        entity.setLine2(line2);
        entity.setZipcode(zipcode);

        return addressRepository.save(entity);
    }

}

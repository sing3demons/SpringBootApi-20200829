package com.sing3demons.springbootapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sing3demons.springbootapi.entity.Address;
import com.sing3demons.springbootapi.entity.User;

public interface AddressRepository extends CrudRepository<Address, String> {
    List<Address> findByUser(User user);

}

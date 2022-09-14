package com.sing3demons.springbootapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sing3demons.springbootapi.entity.User;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}

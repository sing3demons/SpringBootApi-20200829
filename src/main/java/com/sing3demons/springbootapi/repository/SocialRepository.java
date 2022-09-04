package com.sing3demons.springbootapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sing3demons.springbootapi.entity.Social;
import com.sing3demons.springbootapi.entity.User;

public interface SocialRepository extends CrudRepository<Social, String> {
    Optional<Social> findByUser(User user);
}

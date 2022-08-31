package com.sing3demons.springbootapi.service;

import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.exception.UserException;
import com.sing3demons.springbootapi.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(String email, String password, String name) throws UserException {

        if (Objects.isNull(email)) {
            throw UserException.emailNull();
        }

        if (Objects.isNull(password)) {
            throw UserException.passwordNull();
        }

        if (Objects.isNull(name)) {
            throw UserException.nameNull();
        }

        if (userRepository.existsByEmail(email)) {
            throw UserException.emailDuplicated();
        }

        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);

        return userRepository.save(entity);
    }

}

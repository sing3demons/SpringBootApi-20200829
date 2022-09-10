package com.sing3demons.springbootapi.service;


import java.util.Objects;
import java.util.Optional;

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

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByID(String id) {
        return userRepository.findById(id);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User create(String email, String password, String name) throws UserException {

        if (Objects.isNull(email))
            throw UserException.emailNull();

        if (Objects.isNull(password))
            throw UserException.passwordNull();

        if (Objects.isNull(name))
            throw UserException.nameNull();

        if (userRepository.existsByEmail(email))
            throw UserException.emailDuplicated();

        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);

        return userRepository.save(entity);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    public User updateName(String id, String name) throws UserException {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }

        User user = opt.get();
        user.setName(name);

        return userRepository.save(user);

    }

    public User updateUser(User entity) {
        return userRepository.save(entity);
    }

  

}

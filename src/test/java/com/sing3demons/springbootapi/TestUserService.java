package com.sing3demons.springbootapi;

import java.util.Optional;

import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.exception.UserException;
import com.sing3demons.springbootapi.service.UserService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserService {
    @Autowired
    private UserService service;

    @Order(1)
    @Test
    void testCreate() throws UserException {
        User user = service.create(TestData.email, TestData.password, TestData.name);
        Assertions.assertNotNull(user);

        Assertions.assertEquals(TestData.email, user.getEmail());
        Assertions.assertEquals(TestData.name, user.getName());
        Assertions.assertTrue(service.matchPassword(TestData.password, user.getPassword()));

    }

    @Test
    void testUpdate() throws UserException {
        Optional<User> opt = service.findByEmail(TestData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        User newUser = service.updateName(user.getId(), TestUpdateData.name);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(TestUpdateData.name, newUser.getName());
    }

    @Test
    void testDelete() {
        Optional<User> opt = service.findByEmail(TestData.email);
        Assertions.assertTrue(opt.isPresent());

        service.deleteUser(opt.get().getId());

        Optional<User> optDelete = service.findByEmail(TestData.email);
        Assertions.assertTrue(optDelete.isEmpty());

    }

    interface TestData {
        String email = "test@test.com";
        String password = "12345";
        String name = "sing";
    }

    interface TestUpdateData {
        String name = "kpsing";
    }

}

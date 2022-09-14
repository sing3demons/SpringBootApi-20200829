package com.sing3demons.springbootapi;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sing3demons.springbootapi.entity.Address;
import com.sing3demons.springbootapi.entity.Social;
import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.exception.UserException;
import com.sing3demons.springbootapi.service.AddressService;
import com.sing3demons.springbootapi.service.SocialService;
import com.sing3demons.springbootapi.service.UserService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserService {
    @Autowired
    private UserService service;

    @Autowired
    private SocialService socialService;

    @Autowired
    private AddressService addressService;

    @Order(1)
    @Test
    void testCreate() throws UserException {
        User user = service.create(TestData.email, TestData.password, TestData.name);
        Assertions.assertNotNull(user);

        Assertions.assertEquals(TestData.email, user.getEmail());
        Assertions.assertEquals(TestData.name, user.getName());
        Assertions.assertTrue(service.matchPassword(TestData.password, user.getPassword()));

    }

    @Order(2)
    @Test
    void testUpdate() throws UserException {
        Optional<User> opt = service.findByEmail(TestData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        User newUser = service.updateName(user.getId(), TestUpdateData.name);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(TestUpdateData.name, newUser.getName());
    }

    @Order(3)
    @Test
    void testCreateSocial() throws UserException {
        Optional<User> opt = service.findByEmail(TestData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        Social social = user.getSocial();
        Assertions.assertNull(social);
        social = socialService.create(user, TestSocialData.facebook, TestSocialData.line,
                TestSocialData.instagram, TestSocialData.tiktok);

        Assertions.assertNotNull(social);
        Assertions.assertEquals(TestSocialData.facebook, social.getFacebook());
    }

    @Order(4)
    @Test
    void testCreateAddress() {
        Optional<User> opt = service.findByEmail(TestData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        List<Address> address = user.getAddresses();
        Assertions.assertTrue(address.isEmpty());

        Address ass = addressService.create(user, AddressTestCreateData.line1, AddressTestCreateData.line2,
                AddressTestCreateData.zipcode);
        Assertions.assertNotNull(ass);
        Assertions.assertEquals(AddressTestCreateData.line1, ass.getLine1());
        Assertions.assertEquals(AddressTestCreateData.line2, ass.getLine2());
        Assertions.assertEquals(AddressTestCreateData.zipcode, ass.getZipcode());
    }

    @Order(9)
    @Test
    void testDelete() {
        Optional<User> opt = service.findByEmail(TestData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        service.deleteById(user.getId());

        Optional<User> optDelete = service.findByEmail(TestData.email);
        Assertions.assertTrue(optDelete.isEmpty());

    }

    interface TestSocialData {
        String facebook = "sing3demons";
        String line = "sing3demons";
        String instagram = "sing3demons";
        String tiktok = "sing3demons";
    }

    interface TestData {
        String email = "test@test.com";
        String password = "12345";
        String name = "sing";
    }

    interface TestUpdateData {
        String name = "kpSing";
    }

    interface AddressTestCreateData {
        String line1 = "123/4";
        String line2 = "bangkok";
        String zipcode = "10100";
    }

}

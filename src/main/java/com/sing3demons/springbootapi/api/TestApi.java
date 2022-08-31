package com.sing3demons.springbootapi.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sing3demons.springbootapi.business.TestBusiness;
import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.FileException;
import com.sing3demons.springbootapi.model.MRegisterRequest;
import com.sing3demons.springbootapi.model.TestResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TestApi {
    // METHOD: 1 field Injection
    // @Autowired

    // METHOD: 2 Constructor Injection
    private final TestBusiness business;

    public TestApi(TestBusiness business) {
        this.business = business;
    }

    @GetMapping("/test")
    public TestResponse test() {
        TestResponse response = new TestResponse();
        response.setName("Sing");
        response.setFood("KFC");
        return response;

    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody MRegisterRequest request) throws BaseException {
        String response = business.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadProfilePicture(MultipartFile file) throws BaseException {
        String response = business.uploadProfilePicture(file);
        return ResponseEntity.ok(response);
    }

}

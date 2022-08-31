package com.sing3demons.springbootapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sing3demons.springbootapi.business.UserBusiness;
import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.UserException;
import com.sing3demons.springbootapi.model.MRegisterRequest;
import com.sing3demons.springbootapi.model.TestResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class UserApi {
    // METHOD: 1 field Injection
    // @Autowired

    // METHOD: 2 Constructor Injection
    private final UserBusiness business;

    public UserApi(UserBusiness business) {
        this.business = business;
    }

    @GetMapping("/users")
    public TestResponse test() {
        TestResponse response = new TestResponse();
        response.setName("Sing");
        response.setFood("KFC");
        return response;

    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@RequestBody MRegisterRequest request) throws UserException {
        User response = business.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadProfilePicture(MultipartFile file) throws BaseException {
        String response = business.uploadProfilePicture(file);
        return ResponseEntity.ok(response);
    }

}

package com.sing3demons.springbootapi.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sing3demons.springbootapi.business.UserBusiness;
import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.UserException;
import com.sing3demons.springbootapi.mapper.UserMapper;
import com.sing3demons.springbootapi.model.LoginRequest;
import com.sing3demons.springbootapi.model.MRegisterRequest;
import com.sing3demons.springbootapi.model.RegisterResponse;
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
    private final UserMapper userMapper;

    public UserApi(UserBusiness business, UserMapper userMapper) {
        this.business = business;
        this.userMapper = userMapper;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) throws UserException {
        User user = business.login(request);
        return ResponseEntity.ok(user);

    }

    @PostMapping(value = "/auth/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody MRegisterRequest request) throws UserException {

        User user = business.register(request);
        RegisterResponse response = userMapper.registerResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadProfilePicture(MultipartFile file) throws BaseException {
        String response = business.uploadProfilePicture(file);
        return ResponseEntity.ok(response);
    }

}

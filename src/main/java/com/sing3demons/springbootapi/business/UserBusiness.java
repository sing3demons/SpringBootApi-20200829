package com.sing3demons.springbootapi.business;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.FileException;
import com.sing3demons.springbootapi.exception.UserException;
import com.sing3demons.springbootapi.model.LoginRequest;
import com.sing3demons.springbootapi.model.MRegisterRequest;
import com.sing3demons.springbootapi.service.UserService;

@Service
public class UserBusiness {
    private final UserService userService;

    public UserBusiness(UserService userService) {
        this.userService = userService;
    }

    public User register(MRegisterRequest request) throws UserException {
        return userService.create(request.getEmail(), request.getPassword(), request.getName());
    }

    public User login(LoginRequest request) throws UserException {
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            // throw login fail, email not found
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get();

        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            // throw login fail, password incorrect
            throw UserException.loginFailPasswordIncorrect();
        }

        return user;
    }

    public String uploadProfilePicture(MultipartFile file) throws BaseException {
        if (file == null) {
            throw FileException.fileNull();
        }

        if (file.getSize() > 1048576 * 2) {
            throw FileException.fileMaxSize();
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            throw FileException.fileUnsupported();
        }

        List<String> supportedTypes = Arrays.asList("image/jpeg", "image/png");
        if (!supportedTypes.contains(contentType)) {
            throw FileException.fileUnsupported();
        }

        return "";
    }

}

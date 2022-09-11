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
import com.sing3demons.springbootapi.service.TokenService;
import com.sing3demons.springbootapi.service.UserService;
import com.sing3demons.springbootapi.util.SecurityUtil;

@Service
public class UserBusiness {
    private final UserService userService;
    private final TokenService tokenService;

    public UserBusiness(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public User register(MRegisterRequest request) throws UserException {
        return userService.create(request.getEmail(), request.getPassword(), request.getName());
    }

    public String login(LoginRequest request) throws UserException {
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

        return tokenService.tokenize(user);
    }

    public String refreshToken() throws UserException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findByID(userId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }
        User user = optUser.get();
        return tokenService.tokenize(user);
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

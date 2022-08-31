package com.sing3demons.springbootapi.business;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.FileException;
import com.sing3demons.springbootapi.exception.UserException;
import com.sing3demons.springbootapi.model.MRegisterRequest;
import com.sing3demons.springbootapi.service.UserService;

@Service
public class UserBusiness {
    private final UserService userService;

    public UserBusiness(UserService userService) {
        this.userService = userService;
    }

    public User register(MRegisterRequest request) throws UserException {
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName());
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

        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}

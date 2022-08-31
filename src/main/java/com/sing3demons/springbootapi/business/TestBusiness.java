package com.sing3demons.springbootapi.business;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sing3demons.springbootapi.exception.BaseException;
import com.sing3demons.springbootapi.exception.FileException;
import com.sing3demons.springbootapi.exception.UserException;
import com.sing3demons.springbootapi.model.MRegisterRequest;

@Service
public class TestBusiness {
    public String register(MRegisterRequest request) throws BaseException {
        if (request == null) {
            throw UserException.requestNull();
        }

        if (Objects.isNull(request.getEmail())) {
            throw UserException.emailNull();
        }
        return "" + request;

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
        }

        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}

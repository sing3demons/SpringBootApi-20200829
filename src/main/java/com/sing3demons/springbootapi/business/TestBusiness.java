package com.sing3demons.springbootapi.business;

import java.util.Objects;
import org.springframework.stereotype.Service;
import com.sing3demons.springbootapi.exception.BaseException;
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
}

package com.sing3demons.springbootapi.exception;

public class UserException extends BaseException {

    public UserException(String code) {
        super("user." + code);
    }

    public static UserException requestNull() {
        return new UserException("register.request.null");
    }

    public static UserException emailNull() {
        return new UserException("register.email.null");
    }

    public static UserException passwordNull() {
        return new UserException("register.password.null");
    }

    public static UserException nameNull() {
        return new UserException("register.name.null");
    }

    public static UserException emailDuplicated() {
        return new UserException("register.email.duplicated");
    }

    public static UserException loginFailEmailNotFound() {
        return new UserException("login.fail");
    }

    public static UserException loginFailPasswordIncorrect() {
        return new UserException("login.fail");
    }

}

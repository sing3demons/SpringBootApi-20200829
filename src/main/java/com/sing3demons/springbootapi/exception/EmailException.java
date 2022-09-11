package com.sing3demons.springbootapi.exception;

public class EmailException extends BaseException {

    protected EmailException(String code) {
        super("email." + code);
    }

    public static EmailException templateNotFound() {
        return new EmailException("template.not.found");
    }
}

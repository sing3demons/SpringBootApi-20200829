package com.sing3demons.springbootapi.exception;

public class ChatException extends BaseException {

    protected ChatException(String code) {
        super("chat"+code);
    }

    public static ChatException accessDenied() {
        return new ChatException("access.denied");
    }

    
}

package com.sing3demons.springbootapi.exception;

public abstract class BaseException extends Exception {
    protected BaseException(String code) {
        super(code);
    }
}

package com.filipecode.libraryApi.exceptions;

public class DuplicateRegisterException extends RuntimeException{
    public DuplicateRegisterException(String message) {
        super(message);
    }
}

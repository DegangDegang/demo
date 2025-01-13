package com.example.demo.domain.image.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class BadFileExtensionException extends DegangException {
    public static final DegangException EXCEPTION = new BadFileExtensionException();
    private BadFileExtensionException() {
        super(ErrorCode.BAD_FILE_EXTENSION);
    }
}

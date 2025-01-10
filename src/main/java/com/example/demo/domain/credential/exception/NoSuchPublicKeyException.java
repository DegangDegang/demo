package com.example.demo.domain.credential.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class NoSuchPublicKeyException extends DegangException {
    public static final DegangException EXCEPTION = new NoSuchPublicKeyException();

    private NoSuchPublicKeyException() {
        super(ErrorCode.NO_SUCH_PUBLIC_KEY);
    }
}

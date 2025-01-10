package com.example.demo.domain.credential.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class NotNullTokenException extends DegangException {

    public static final DegangException EXCEPTION = new NotNullTokenException();

    private NotNullTokenException() {
        super(ErrorCode.NOT_NULL_TOKEN);
    }


}

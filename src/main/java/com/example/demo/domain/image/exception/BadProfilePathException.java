package com.example.demo.domain.image.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class BadProfilePathException extends DegangException {

    public static final DegangException EXCEPTION = new BadProfilePathException();
    private BadProfilePathException() {
        super(ErrorCode.BAD_PROFILE_PATH);
    }
}

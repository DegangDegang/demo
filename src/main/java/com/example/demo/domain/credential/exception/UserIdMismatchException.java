package com.example.demo.domain.credential.exception;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class UserIdMismatchException extends DegangException {

    public static final DegangException EXCEPTION = new UserIdMismatchException();

    private UserIdMismatchException() {
        super(ErrorCode.MISMATCH_USER_OAUTH_ID);
    }


}

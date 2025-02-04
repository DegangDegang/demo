package com.example.demo.domain.shortessay.exception;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class UnauthorizedShortEssayException extends DegangException {

    public static DegangException EXCEPTION = new UnauthorizedShortEssayException();

    private UnauthorizedShortEssayException() {
        super(ErrorCode.UNAUTHORIZED_SHORT_ESSAY);
    }
}

package com.example.demo.domain.shortessay.exception;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class UnauthorizedShortEssayDeletionException extends DegangException {

    public static DegangException EXCEPTION = new UnauthorizedShortEssayDeletionException();

    private UnauthorizedShortEssayDeletionException() {
        super(ErrorCode.UNAUTHORIZED_SHORT_ESSAY);
    }
}

package com.example.demo.domain.essay.exception;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class EssayLikeAlreadyExistsException extends DegangException {
    public static final DegangException EXCEPTION = new EssayLikeAlreadyExistsException();
    private EssayLikeAlreadyExistsException() {
        super(ErrorCode.ESSAY_LIKE_ALREADY_EXISTS);
    }
}

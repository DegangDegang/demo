package com.example.demo.domain.essay.exception;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class EssayNotFoundException extends DegangException {
    public static final DegangException EXCEPTION = new EssayNotFoundException();
    private EssayNotFoundException() {
        super(ErrorCode.ESSAY_NOT_FOUND);
    }
}

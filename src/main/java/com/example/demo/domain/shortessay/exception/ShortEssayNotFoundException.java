package com.example.demo.domain.shortessay.exception;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class ShortEssayNotFoundException extends DegangException {

    public static DegangException EXCEPTION = new ShortEssayNotFoundException();

    private ShortEssayNotFoundException() {
        super(ErrorCode.SHORT_ESSAY_NOT_FOUND);
    }

}

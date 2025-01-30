package com.example.demo.domain.shortessay.exception;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class ShortEssayLikeNotFoundException extends DegangException {

    public static DegangException EXCEPTION = new ShortEssayLikeNotFoundException();

    private ShortEssayLikeNotFoundException() {
        super(ErrorCode.SHORT_ESSAY_LIKE_NOT_FOUND);
    }

}

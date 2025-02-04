package com.example.demo.domain.essay.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class EssayLikeNotFoundException extends DegangException {
    public static final DegangException EXCEPTION = new EssayLikeNotFoundException();
    private EssayLikeNotFoundException() {
        super(ErrorCode.ESSAY_LIKE_NOT_FOUND);
    }
}

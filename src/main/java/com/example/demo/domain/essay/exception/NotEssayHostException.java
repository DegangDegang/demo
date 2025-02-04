package com.example.demo.domain.essay.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class NotEssayHostException extends DegangException {

    public static final DegangException EXCEPTION = new NotEssayHostException();
    private NotEssayHostException() {
        super(ErrorCode.ESSAY_NOT_HOST);
    }
}

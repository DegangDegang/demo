package com.example.demo.domain.shortessay.exception;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class AlreadyLikedException extends DegangException {

    public static DegangException EXCEPTION = new AlreadyLikedException();

    private AlreadyLikedException() {
        super(ErrorCode.ALREADY_LIKED);
    }

}

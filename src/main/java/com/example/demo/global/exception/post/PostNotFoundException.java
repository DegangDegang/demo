package com.example.demo.global.exception.post;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class PostNotFoundException extends DegangException {

    public static final DegangException EXCEPTION = new PostNotFoundException();

    private PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}

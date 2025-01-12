package com.example.demo.global.exception.post;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class PostPermissionException extends DegangException {

    public static final DegangException EXCEPTION = new PostPermissionException();

    private PostPermissionException() {
        super(ErrorCode.POST_PERMISSION);
    }

}

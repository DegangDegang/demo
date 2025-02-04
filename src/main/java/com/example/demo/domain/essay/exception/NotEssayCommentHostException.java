package com.example.demo.domain.essay.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class NotEssayCommentHostException extends DegangException {

    public static final DegangException EXCEPTION = new NotEssayCommentHostException();
    private NotEssayCommentHostException() {
        super(ErrorCode.ESSAY_COMMENT_NOT_HOST);
    }
}

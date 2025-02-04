package com.example.demo.domain.essay.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class EssayCommentNotFoundException extends DegangException {
    public static final DegangException EXCEPTION = new EssayCommentNotFoundException();
    private EssayCommentNotFoundException() {
        super(ErrorCode.ESSAY_COMMENT_NOT_FOUND);
    }
}

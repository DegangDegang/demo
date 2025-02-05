package com.example.demo.domain.asset.sentence.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class SentenceNotFoundException extends DegangException {
    public static final DegangException EXCEPTION = new SentenceNotFoundException();
    private SentenceNotFoundException() {
        super(ErrorCode.SENTENCE_COMMENT_NOT_FOUND);
    }
}

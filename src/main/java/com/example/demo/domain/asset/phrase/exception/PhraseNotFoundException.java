package com.example.demo.domain.asset.phrase.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class PhraseNotFoundException extends DegangException {
    public static final DegangException EXCEPTION = new PhraseNotFoundException();
    private PhraseNotFoundException() {
        super(ErrorCode.PHRASE_COMMENT_NOT_FOUND);
    }
}

package com.example.demo.domain.essay.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class EssayDraftNotFoundException extends DegangException {
    public static final DegangException EXCEPTION = new EssayDraftNotFoundException();
    private EssayDraftNotFoundException() {
        super(ErrorCode.ESSAY_DRAFT_NOT_FOUND);
    }
}

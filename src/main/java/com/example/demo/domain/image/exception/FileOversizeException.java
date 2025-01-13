package com.example.demo.domain.image.exception;

import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class FileOversizeException extends DegangException {

    public static final DegangException EXCEPTION = new FileOversizeException();

    private FileOversizeException() {
        super(ErrorCode.FILE_OVER_SIZE);
    }
}

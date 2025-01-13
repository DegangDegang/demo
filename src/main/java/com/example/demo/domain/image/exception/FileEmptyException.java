package com.example.demo.domain.image.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class FileEmptyException extends DegangException {

    public static final DegangException EXCEPTION = new FileEmptyException();
    private FileEmptyException() {
        super(ErrorCode.FILE_EMPTY);
    }
}

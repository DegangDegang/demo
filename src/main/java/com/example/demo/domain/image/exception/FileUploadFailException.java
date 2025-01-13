package com.example.demo.domain.image.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class FileUploadFailException extends DegangException {

    public static final DegangException EXCEPTION = new FileUploadFailException();

    private FileUploadFailException() {
        super(ErrorCode.FILE_UPLOAD_FAIL);
    }
}

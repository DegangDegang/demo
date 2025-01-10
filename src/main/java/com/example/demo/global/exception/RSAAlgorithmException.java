package com.example.demo.global.exception;


import com.example.demo.global.error.exception.DegangException;
import com.example.demo.global.error.exception.ErrorCode;

public class RSAAlgorithmException extends DegangException {

    public static final DegangException EXCEPTION = new RSAAlgorithmException();


    private RSAAlgorithmException() {
        super(ErrorCode.NO_SUCH_RSA_ALGORITHM);
    }
}

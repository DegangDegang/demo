package com.example.demo.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DegangException extends RuntimeException{

    private ErrorCode errorCode;
}

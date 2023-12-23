package com.example.janackoverflow.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@AllArgsConstructor
public class BusinessLogicException extends RuntimeException {
    private final ExceptionCode exceptionCode;

//    @Builder
//    public BusinessLogicException(ExceptionCode exceptionCode, String message) {
//        super(message);
//        this.exceptionCode = exceptionCode;
//    }
//
//    @Builder
//    public BusinessLogicException(ExceptionCode exceptionCode) {
//        super(exceptionCode.getMessage());
//        this.exceptionCode = exceptionCode;
//    }
}
package com.example.janackoverflow.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    ACCOUNT_NOT_FOUND(404, "계좌가 존재하지 않습니다."),
    ACCOUNT_EXIST(404, "계좌가 이미 존재합니다."),
    RULE_NOT_FOUND(404, "규칙이 존재하지 않습니다."),
    ERROR_NOT_FOUND(404, "에러가 존재하지 않습니다."),
    ERROR_EXIST(404, "에러가 이미 존재합니다."),;

    private final int status;

    private final String message;

    ExceptionCode(int status, String message){
        this.status = status;
        this.message = message;
    }
}

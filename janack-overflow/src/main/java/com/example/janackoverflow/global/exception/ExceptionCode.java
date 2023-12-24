package com.example.janackoverflow.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    UNAUTHORIZED(401, "회원정보가 없습니다."),
    USER_NOT_FOUND(404, "사용자가 존재하지 않습니다."),
    ACCOUNT_NOT_FOUND(404, "계좌가 존재하지 않습니다."),
    ACCOUNT_EXIST(404, "계좌가 이미 존재합니다."),
    RULE_NOT_FOUND(404, "규칙이 존재하지 않습니다."),
    ERROR_NOT_FOUND(404, "에러가 존재하지 않습니다."),
    ERROR_EXIST(404, "에러가 이미 존재합니다."),
    DUPLICATE_RESOURCE(409, "데이터가 이미 존재합니다.");

    private final int status;

    private final String message;

}

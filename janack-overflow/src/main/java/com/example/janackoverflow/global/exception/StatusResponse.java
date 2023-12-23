package com.example.janackoverflow.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class StatusResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String code;
    private final String message;

    public static ResponseEntity<StatusResponse> toResponseEntity(ExceptionCode exceptionCode) {
        return ResponseEntity
                .status(exceptionCode.getStatus())
                .body(StatusResponse.builder()
                        .status(exceptionCode.getStatus())
                        .code(exceptionCode.name())
                        .message(exceptionCode.getMessage())
                        .build()
                );
    }
}
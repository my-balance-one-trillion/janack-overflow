package com.example.janackoverflow.global.security.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailDTO {
    private String to;
    private String subject;
    private String message;
}

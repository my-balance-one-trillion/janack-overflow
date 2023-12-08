package com.example.janackoverflow.saving.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class InputAccountResponseDTO {
    private Long id;
    private Long userId;
    private String acntNum;
    private String acntName;

}

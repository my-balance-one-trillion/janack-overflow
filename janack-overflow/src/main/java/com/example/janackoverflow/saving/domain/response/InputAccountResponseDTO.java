package com.example.janackoverflow.saving.domain.response;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputAccountResponseDTO {

    private String acntName;
    private String goalName;
    private int goalNum;

}

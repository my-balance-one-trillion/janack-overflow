package com.example.janackoverflow.saving.domain.response;

import com.example.janackoverflow.saving.entity.Rule;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleResponseDTO {

    private int underThirty;
    private int underHour;
    private int underThreeHour;
    private int overThreeHour;

    public static RuleResponseDTO toDto(Rule rule){
        return RuleResponseDTO.builder()
                .underThirty(rule.getUnderThirty())
                .underHour(rule.getUnderHour())
                .underThreeHour(rule.getUnderThreeHour())
                .overThreeHour(rule.getOverThreeHour())
                .build();
    }
}

package com.example.janackoverflow.issue.domain.response;

import com.example.janackoverflow.issue.entity.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolutionResponseDTO {

    private LocalDateTime createdAt;

    public static SolutionResponseDTO toDto(Solution solution){
        return SolutionResponseDTO.builder()
                .createdAt(solution.getCreatedAt())
                .build();
    }
}

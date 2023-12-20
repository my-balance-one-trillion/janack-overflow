package com.example.janackoverflow.issue.domain;

import com.example.janackoverflow.issue.entity.Issue;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SolutionDTO {
    private Long id;

    private String content;

    private String code;
}

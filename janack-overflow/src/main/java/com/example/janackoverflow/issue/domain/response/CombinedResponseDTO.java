package com.example.janackoverflow.issue.domain.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CombinedResponseDTO {
    private IssueResponseDTO issueResponseDTO;
    private SolutionResponseDTO solutionResponseDTO;
}

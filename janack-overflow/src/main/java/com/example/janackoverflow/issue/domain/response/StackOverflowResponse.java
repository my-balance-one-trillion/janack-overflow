package com.example.janackoverflow.issue.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class StackOverflowResponse {
    private List<String> tags;
    private String link;
    private String title;
}

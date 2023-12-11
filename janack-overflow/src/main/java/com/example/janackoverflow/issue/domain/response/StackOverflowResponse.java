package com.example.janackoverflow.issue.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
public class StackOverflowResponse {
    private final List<String> tags;
    private final String link;
    private final String title;

    public StackOverflowResponse(List<String> tags, String link, String title) {
        this.tags = tags;
        this.link = link;
        this.title = title;
    }
}

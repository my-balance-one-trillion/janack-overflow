package com.example.janackoverflow.community.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class MediumArticle {
    String title;
    String subTitle;
    String imgUrl;
    String url;
    int claps;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime publishedAt;
}

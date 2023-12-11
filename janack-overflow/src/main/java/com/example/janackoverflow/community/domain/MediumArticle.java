package com.example.janackoverflow.community.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MediumArticle {
    String title;
    String subTitle;
    String imgUrl;
    String url;
}

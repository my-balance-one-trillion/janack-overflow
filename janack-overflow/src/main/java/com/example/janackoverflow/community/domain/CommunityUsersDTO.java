package com.example.janackoverflow.community.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class CommunityUsersDTO {
    private Long id;
    private String nickname;
    private String profileImage;
}

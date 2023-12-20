package com.example.janackoverflow.community.domain.likes;

import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class LikesId implements Serializable {
    @Serial
    private static final long serialVersionUID = -5014168290311695319L;

//    public Long userId;
//
//    public Long issueId;

    public Users users;
    public Issue issue;
}

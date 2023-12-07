package com.example.janackoverflow.community.service;

import com.example.janackoverflow.community.domain.likes.LikesDTO;
import com.example.janackoverflow.community.repository.LikesRepository;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikesService {

    private final LikesRepository likesRespository;

    public LikesService (LikesRepository likesRespository) {
        this.likesRespository = likesRespository;
    }

    // 게시물에 해당하는 좋아요 갯수
    public long getIssueLikes(Long issueId) {
        long likesCnt = likesRespository.countLikesByIssueId(issueId);

        log.info(issueId + "'s Likes cnt : " + likesCnt);

        return likesCnt;
    }

    /**
     * 좋아요 추가
     * @param usersId
     * @param issueId
     */
    public void saveLikes (Long usersId, Long issueId) {
        // Users users = usersRepository.findById(usersId);
        // Issue issue = issueRepository.findById(issueId);

        LikesDTO.LikesRequestDTO likesRequestDTO = LikesDTO.LikesRequestDTO.builder()
                        .users(Users.builder().id(usersId).build())
                        .issue(Issue.builder().id(issueId).build())
                        .build();

        likesRespository.save(likesRequestDTO.toEntity());
    }
}

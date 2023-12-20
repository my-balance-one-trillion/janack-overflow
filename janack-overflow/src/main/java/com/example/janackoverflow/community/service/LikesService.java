package com.example.janackoverflow.community.service;

import com.example.janackoverflow.community.domain.likes.LikesDTO;
import com.example.janackoverflow.community.entity.Likes;
import com.example.janackoverflow.community.repository.LikesRepository;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class LikesService {

    private final LikesRepository likesRespository;

    private final UsersRepository usersRepository;

    private final IssueRepository issueRepository;

    public LikesService (LikesRepository likesRespository, UsersRepository usersRepository, IssueRepository issueRepository) {
        this.likesRespository = likesRespository;
        this.usersRepository = usersRepository;
        this.issueRepository = issueRepository;
    }

    // 게시물에 해당하는 좋아요 갯수
    public long getIssueLikes(Long issueId) {
        return likesRespository.countLikesByIssueId(issueId);
    }

    /**
     * 좋아요 추가
     * @param usersId
     * @param issueId
     */
    @Transactional
    public void saveLikes (Long usersId, Long issueId) {
        log.info("usersId : " + usersId);
        Users users = usersRepository.findById(usersId).orElseThrow(() -> new IllegalArgumentException("user를 찾을 수 없음"));
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new IllegalArgumentException("issue를 찾을 수 없음"));

        LikesDTO.LikesRequestDTO likesRequestDTO = LikesDTO.LikesRequestDTO.builder()
                        .users(users)
                        .issue(issue)
                        .build();

        likesRespository.save(likesRequestDTO.toEntity());
    }

    @Transactional
    public long deleteLike(long issueId, long usersId) {
        return likesRespository.deleteByIssueIdAndUsersId(issueId, usersId);
    }

    public long selectLikes(Long usersId, Long issueId) {
        return likesRespository.countByIssueIdAndUsersId(issueId, usersId);
    }

    public long selectLikesCnt(Long issueId) {
        return likesRespository.countByIssueId(issueId);
    }
}

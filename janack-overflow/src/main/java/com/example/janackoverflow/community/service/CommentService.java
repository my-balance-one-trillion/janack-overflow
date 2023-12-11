package com.example.janackoverflow.community.service;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.repository.CommentRepository;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;
    private final IssueRepository issueRepository;


    public CommentService (CommentRepository commentRepository,
                           UsersRepository usersRepository,
                           IssueRepository issueRepository) {
        this.commentRepository = commentRepository;
        this.usersRepository= usersRepository;
        this.issueRepository = issueRepository;
    }

    @Transactional
    public void createComment(CommentDTO.CommentRequestDto requestDto, Long issueId, Long usersId) {
        log.info("comment : " + requestDto.getContent());

        Users users = usersRepository.findById(usersId).orElseThrow(() -> new IllegalArgumentException("user를 찾을 수 없음"));
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new IllegalArgumentException("issue를 찾을 수 없음"));

        CommentDTO.CommentRequestDto commentRequestDto = CommentDTO.CommentRequestDto.builder()
                .content(requestDto.getContent())
                .users(users)
                .issue(issue)
                .build();

        commentRepository.save(commentRequestDto.toEntity(issue, users));
    }
}

package com.example.janackoverflow.community.service;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.repository.CommentListRepositoryImpl;
import com.example.janackoverflow.community.repository.CommentRepository;
import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;
    private final IssueRepository issueRepository;
    private final CommentListRepositoryImpl commentListRepositoryImpl;

    public CommentService (CommentRepository commentRepository,
                           UsersRepository usersRepository,
                           IssueRepository issueRepository, CommentListRepositoryImpl commentListRepositoryImpl) {
        this.commentRepository = commentRepository;
        this.usersRepository= usersRepository;
        this.issueRepository = issueRepository;
        this.commentListRepositoryImpl = commentListRepositoryImpl;
    }

    @Transactional
    public void createComment(CommentDTO.CommentRequestDto requestDto, Long issueId, Long usersId) {
        log.info("comment : " + requestDto.getContent());

        Users users = usersRepository.findById(usersId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND));

        CommentDTO.CommentRequestDto commentRequestDto = CommentDTO.CommentRequestDto.builder()
                .content(requestDto.getContent())
                .users(users)
                .issue(issue)
                .build();

        commentRepository.save(commentRequestDto.toEntity(issue, users));
    }

    public Page<CommentDTO.ResponseDto> getCommentList(long issueId, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5, Sort.by(Sort.Direction.DESC, "id"));
        Page<Comment> commentList =commentListRepositoryImpl.selectCommentList(issueId, pageable);
        List<CommentDTO.ResponseDto> commentPageList = commentListRepositoryImpl.selectCommentList(issueId, pageable).map(Comment :: toDto).toList();

        return new PageImpl<>(commentPageList, pageable, commentList.getTotalElements());
    }

}

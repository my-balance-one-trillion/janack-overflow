package com.example.janackoverflow.community.service;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.repository.CommentRepository;
import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommunityService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UsersRepository usersRepository;
    private final LikesService likesService;

    public CommunityService (CommentRepository commentRepository,
                             IssueRepository issueRepository,
                             LikesService likesService,
                             UsersRepository usersRepository) {
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.likesService = likesService;
        this.usersRepository = usersRepository;
    }

//    public Page<CommentDTO.ResponseDto> getCommentList(Long issueId) {
//        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
//
//        // 페이져블 객체와 errorId로 모든 comment를 들고온다.
//        Page<Comment> commentList = commentRepository.findAllByIssue_IdOrderByCreatedAtDesc(1L, pageable);
//        log.info(" commentlist +++ : " + commentList.get().findFirst().get().getUsers().getId());
//        // 댓글DtoList
////        List<CommentDTO.CommentResponseDto> commentResponseDtolist = commentList.stream().map(comment -> CommentDTO.CommentResponseDto.builder()
////                .id(comment.getId())
////                .comment(comment.getContent())
////                .build()).toList();
////        List<CommentDTO.CommentResponseDto> commentResponseDtolist = commentList.stream().map(comment -> CommentDTO.CommentResponseDto.builder()
////                .id(comment.getId())
////                .comment(comment.getContent())
////                .createdAt(comment.getCreatedAt())
////                .build().toDto(comment.getUsers())).toList();
////
////        Page<CommentDTO.CommentResponseDto> commentPage = new PageImpl<>(commentResponseDtolist, pageable, commentList.getTotalElements());
////
////        log.info("get Content : " + commentPage.getContent().get(0));
////        log.info("page : " + commentPage.getTotalPages());
////        log.info("total : " + commentPage.getTotalElements());
//        return null;
////        return commentPage;
//    }

    public Page<IssueDTO.ResponseDTO> getSolvedIssueList() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));

        // 페이져블 객체와 errorId로 모든 comment를 들고온다.
        Page<Issue> issueList = issueRepository.findAll(pageable);
//        .findAllByIssue_IdOrderByCreatedAtDesc(1L, pageable);
        // 댓글DtoList

        List<IssueDTO.ResponseDTO> issueResponseDtoList = issueList.stream().map(issue -> issue.toDto(likesService.getIssueLikes(issue.getId()), usersRepository.findById(issue.getUsers().getId()).get().toIssueDto())).toList();

        Page<IssueDTO.ResponseDTO> issuePage = new PageImpl<>(issueResponseDtoList, pageable, issueList.getTotalElements());

        log.info("get Content : " + issuePage.getContent().get(0));
        log.info("page : " + issuePage.getTotalPages());
        log.info("total : " + issuePage.getTotalElements());

        return issuePage;
    }

    @Transactional
    public IssueDTO.ResponseDTO detailSolvedIssue(long issueId) {
        // 이슈, 좋아요, 댓글
//        IssueDTO.ResponseDTO responseDTO = issueRepository.findById(issueId).ifPresent(issue -> issue.toDto(likesService.getIssueLikes(issue.getId()), usersRepository.findById(issue.getUsers().getId()).get().toIssueDto()));
        Issue issueView = issueRepository.findById(issueId).orElseThrow(() -> new IllegalArgumentException("해당 이슈가 없습니다."));
        issueView.updateViews();
        log.info("@@@@@@@@@@@@view : " + issueView.getViews());
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));


        // TODO : orElseThrow 수정
        IssueDTO.ResponseDTO responseDTO = issueRepository.findById(issueId).map(issue -> issue.toDetailDto(likesService.getIssueLikes(issue.getId()), usersRepository.findById(issue.getUsers().getId()).get().toIssueDto(), commentRepository.findAllByIssue_IdOrderByCreatedAtDesc(issueId, pageable).stream().map(Comment::toDto).toList())).orElseThrow(() -> new IllegalArgumentException("없는 이슈번호입니다."));

        return responseDTO;
    }
}

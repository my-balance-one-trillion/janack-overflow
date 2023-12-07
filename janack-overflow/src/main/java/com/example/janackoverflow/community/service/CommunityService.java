package com.example.janackoverflow.community.service;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommunityService {
    private final CommentRepository commentRepository;
    public CommunityService (CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * 게시글의 댓글 리스트
     *
     * @param errorId
     * @return
     */
    public Page<CommentDTO.CommentResponseDto> getCommentList(Long errorId) {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));

        // 페이져블 객체와 errorId로 모든 comment를 들고온다.
        Page<Comment> commentList = commentRepository.findAllByError_IdOrderByCreatedAtDesc(errorId, pageable);

        // 댓글DtoList
        List<CommentDTO.CommentResponseDto> commentResponseDtolist = commentList.stream().map(comment -> CommentDTO.CommentResponseDto.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .build()).toList();

        Page<CommentDTO.CommentResponseDto> commentPage = new PageImpl<>(commentResponseDtolist, pageable, commentList.getTotalElements());

        log.info("get Content : " + commentPage.getContent().get(0));
        log.info("page : " + commentPage.getTotalPages());
        log.info("total : " + commentPage.getTotalElements());

        return commentPage;
    }


}

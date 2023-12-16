package com.example.janackoverflow.community.controller;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.entity.Likes;
import com.example.janackoverflow.community.service.CommentService;
import com.example.janackoverflow.community.service.CommunityService;
import com.example.janackoverflow.community.service.LikesService;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.service.IssueService;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping(value = "/community")
public class CommunityController {
    private final CommunityService communityService;
    private final LikesService likesService;
    private final CommentService commentService;
    private final IssueService issueService;

    public CommunityController(CommunityService communityService, LikesService likesService, CommentService commentService, IssueService issueService) {
        this.communityService = communityService;
        this.likesService = likesService;
        this.commentService = commentService;
        this.issueService = issueService;
    }


    @GetMapping(value = "/likes/{issueId}")
    public ResponseEntity<?> getLikesCnt(@PathVariable(name = "issueId") Long issueId) {
        return ResponseEntity.ok(likesService.selectLikesCnt(issueId));
    }
    // 스프링 시큐리티 구현 후에 현재 로그인한 사람 정보받아서 UserId 넘겨줌
    @GetMapping(value = "/likes/{issueId}/{usersId}")
    public ResponseEntity<?> getLikes( @PathVariable(name = "usersId") Long usersId,
                                      @AuthenticationPrincipal NowUserDetails nowUserDetails,
                                      @PathVariable(name = "issueId") Long issueId) {

        Long tokenUserId = nowUserDetails.getUser().getId();
        log.info("tokenUsersId : " + tokenUserId );
        if(!Objects.equals(usersId, tokenUserId)) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println("@@@@@@@@@@@@@@@@@");
        if( likesService.selectLikes(usersId, issueId) > 0) {
            System.out.println("-------------------");
            return ResponseEntity.ok().build();
        } else {
            System.out.println("################");
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/likes/{issueId}/{usersId}")
    public ResponseEntity<Likes> createLikes( @PathVariable(name = "usersId") Long usersId,
                                              @AuthenticationPrincipal NowUserDetails nowUserDetails,
                                              @PathVariable(name = "issueId") Long issueId) {

        Long tokenUserId = nowUserDetails.getUser().getId();
        if(!Objects.equals(usersId, tokenUserId)) {
            return ResponseEntity.badRequest().build();
        }
        likesService.saveLikes(tokenUserId, issueId);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/comment/{issueId}")
    public ResponseEntity<Page<CommentDTO.ResponseDto>> getCommentList(@PathVariable(value = "issueId") long issueId,
                                            @RequestParam(required = false, defaultValue = "0", name = "pageNo") int pageNo) {
        log.info("pageNo : " + pageNo);
        Page<CommentDTO.ResponseDto> comments = commentService.getCommentList(issueId, pageNo);
        log.info("comments : " + comments.getTotalElements());
//        if( !comments.isEmpty() ) {
            return ResponseEntity.ok(comments);
//        } else {
//            return ResponseEntity.notFound().build();
//        }

    }

    @PostMapping(value = "/comment/{issueId}/{usersId}")
    public ResponseEntity<?> createComment(@PathVariable(name = "issueId") Long issueId,
                                                 @PathVariable(name = "usersId") Long usersId,
                                                 @AuthenticationPrincipal NowUserDetails nowUserDetails,
                                                 @RequestBody CommentDTO.CommentRequestDto commentRequestDto){
        log.info(commentRequestDto.getContent());
        Long tokenUserId = nowUserDetails.getUser().getId();
        if(!Objects.equals(usersId, tokenUserId)) {
            return ResponseEntity.badRequest().build();
        }
        commentService.createComment(commentRequestDto, issueId, tokenUserId);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/solvedissue")
    public ResponseEntity<Page<IssueDTO.ResponseDTO>> getCommunity(@RequestParam(name = "order", required = false) String order,
                                                                   @RequestParam(name = "category", required = false) String category,
                                                                   @RequestParam(required = false, defaultValue = "0", name = "pageNo") int pageNo) {
//        long allLikes = likesService.getIssueLikes(1L);
        log.info("/solvedissue ,  order: " + order +  ", pageNo : " + pageNo + ", category : " + category);
        return ResponseEntity.ok(communityService.getSolvedIssueList(order, pageNo));
    }

    @GetMapping(value = "/detail/{issueId}")
    public ResponseEntity<IssueDTO.ResponseDTO> getIssueDetail(@PathVariable(value = "issueId") long issueId) {
        return ResponseEntity.ok(communityService.detailSolvedIssue(issueId));
    }

    @Transactional
    @DeleteMapping(value = "likes/{issueId}/{usersId}")
    public ResponseEntity<String> deleteLike(@PathVariable(value = "issueId") long issueId,
                                             @AuthenticationPrincipal NowUserDetails nowUserDetails,
                                             @PathVariable(value = "usersId") long usersId) {
        Long tokenUserId = nowUserDetails.getUser().getId();
        if(!Objects.equals(usersId, tokenUserId)) {
            return ResponseEntity.badRequest().build();
        }

        if ( likesService.deleteLike(issueId, tokenUserId ) < 0 ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("좋아요 취소가 실패하였습니다.");
        }

        long cnt = likesService.selectLikes(tokenUserId, issueId);
        log.info("@@@@@@@@@like cnt :" + cnt);

        return ResponseEntity.ok("좋아요가 취소되었습니다.");
    }


    @GetMapping(value = "/solvedissue/search")
    public ResponseEntity<Page<IssueDTO.ResponseDTO>> search(@RequestParam(value = "title") String title, @RequestParam(required = false, defaultValue = "0", name = "pageNo") int pageNo,
                                                                     @RequestParam(value = "category", required = false) String category) {

        log.info("/solvedissue/search : " + title + ", pageNo : " + pageNo + ", category : " + category);
        return ResponseEntity.ok(communityService.search(title, category, pageNo));
    }

    @GetMapping(value = "/solvedissue/filter")
    public ResponseEntity<Page<IssueDTO.ResponseDTO>> filterCategory(@RequestParam(value = "category", required = false) String category,
                                                                     @RequestParam(required = false, defaultValue = "0", name = "pageNo") int pageNo) {
        log.info("/solvedissue/filter : " + ", pageNo : " + pageNo + ", category : " + category);
        return ResponseEntity.ok(communityService.getIssueBySelectedCategory(category, pageNo));
    }
}


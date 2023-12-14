package com.example.janackoverflow.community.controller;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.entity.Likes;
import com.example.janackoverflow.community.service.CommentService;
import com.example.janackoverflow.community.service.CommunityService;
import com.example.janackoverflow.community.service.LikesService;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                                      @PathVariable(name = "issueId") Long issueId) {
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
                                              @PathVariable(name = "issueId") Long issueId) {
        likesService.saveLikes(usersId, issueId);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/comment/{issueId}")
    public ResponseEntity<?> getCommentList(@PathVariable(value = "issueId") long issueId) {
        List<CommentDTO.ResponseDto> responseDtoList = commentService.getCommentList(issueId);

        if( !responseDtoList.isEmpty() ) {
            return ResponseEntity.ok(responseDtoList);

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(value = "/comment/{issueId}/{usersId}")
    public ResponseEntity<Comment> createComment(@PathVariable(name = "issueId") Long issueId,
                                                 @PathVariable(name = "usersId") Long usersId,
                                                 @RequestBody CommentDTO.CommentRequestDto commentRequestDto){
        log.info(commentRequestDto.getContent());
        commentService.createComment(commentRequestDto, issueId, usersId);

        return null;
    }

    @GetMapping(value = "/solvedissue")
    public ResponseEntity<Page<IssueDTO.ResponseDTO>> getCommunity(@RequestParam(name = "order", required = false) String order,
                                                                   @RequestParam(name = "category", required = false) String category,
                                                                   @RequestParam(required = false, defaultValue = "0", name = "pageNo") int pageNo) {
//        long allLikes = likesService.getIssueLikes(1L);
        return ResponseEntity.ok(communityService.getSolvedIssueList(order, category, pageNo));
    }

    @GetMapping(value = "/detail/{issueId}")
    public ResponseEntity<IssueDTO.ResponseDTO> getIssueDetail(@PathVariable(value = "issueId") long issueId) {
        return ResponseEntity.ok(communityService.detailSolvedIssue(issueId));
    }

    @Transactional
    @DeleteMapping(value = "likes/{issueId}/{usersId}")
    public ResponseEntity<String> deleteLike(@PathVariable(value = "issueId") long issueId,
                                             @PathVariable(value = "usersId") long usersId) {
        if ( likesService.deleteLike(issueId, usersId ) < 0 ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("좋아요 취소가 실패하였습니다.");
        };
        long cnt = likesService.selectLikes(usersId, issueId);
        log.info("@@@@@@@@@like cnt :" + cnt);
        return ResponseEntity.ok("좋아요가 취소되었습니다.");
    }

//    @GetMapping(value = "/solvedissue/search")
//    public ResponseEntity<Page<IssueDTO.ResponseDTO>> searchKeyword(@RequestParam(value = "keyword") String keyword) {
//        System.out.println(keyword);
//        return ResponseEntity.ok(communityService.searchKeyword(keyword));
//    }

    @GetMapping(value = "/solvedissue/search")
    public ResponseEntity<Page<IssueDTO.ResponseDTO>> search(@RequestParam(value = "title", required = false) String title,
                                                                     @RequestParam(value = "category", required = false) String category) {
        return ResponseEntity.ok(communityService.search(title, category));
    }

    @GetMapping(value = "/solvedissue/filter")
    public ResponseEntity<Page<IssueDTO.ResponseDTO>> filterCategory(@RequestParam(value = "category", required = false) String category,
                                                                     @RequestParam(required = false, defaultValue = "0", name = "pageNo") int pageNo) {
        log.info("category : " + category);
        return ResponseEntity.ok(communityService.getIssueBySelectedCategory(category, pageNo));
    }
}


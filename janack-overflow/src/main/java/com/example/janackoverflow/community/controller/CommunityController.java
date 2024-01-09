package com.example.janackoverflow.community.controller;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.domain.MediumArticle;
import com.example.janackoverflow.community.entity.Likes;
import com.example.janackoverflow.community.service.CommentService;
import com.example.janackoverflow.community.service.CommunityService;
import com.example.janackoverflow.community.service.LikesService;
import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.service.IssueService;
import com.example.janackoverflow.mypage.service.MypageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Tag(name = "커뮤니티 Controller", description = "커뮤니티 관련 API")
@RestController
@Slf4j
@RequestMapping(value = "/community")
public class CommunityController {
    private final CommunityService communityService;
    private final LikesService likesService;
    private final CommentService commentService;
    private final MypageService mypageService;

    public CommunityController(CommunityService communityService, LikesService likesService, CommentService commentService, MypageService mypageService) {
        this.communityService = communityService;
        this.likesService = likesService;
        this.commentService = commentService;
        this.mypageService = mypageService;
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

        } else if( likesService.selectLikes(usersId, issueId) > 0) {
            return ResponseEntity.ok().build();

        } else {
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

        return ResponseEntity.ok(comments);
        // TODO 댓글이 없는 경우
    }

    @PostMapping(value = "/comment/{issueId}/{usersId}")
    public ResponseEntity<?> createComment(@PathVariable(name = "issueId") Long issueId,
                                                 @PathVariable(name = "usersId") Long usersId,
                                                 @AuthenticationPrincipal NowUserDetails nowUserDetails,
                                                 @RequestBody CommentDTO.CommentRequestDto commentRequestDto){
        log.info(commentRequestDto.getContent());
        System.out.println(commentRequestDto.getContent());
        Long tokenUserId = nowUserDetails.getUser().getId();
        if(!Objects.equals(usersId, tokenUserId)) {
            return ResponseEntity.badRequest().build();
        }
        commentService.createComment(commentRequestDto, issueId, tokenUserId);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/solvedissue/{issueId}/articles")
    public ResponseEntity<List<MediumArticle>> getArticles(@PathVariable("issueId")long issueId){
        try {
            List<MediumArticle> list =  communityService.getMediumApi(issueId);
            if(list == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(list);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(value = "/solvedissue")
    public ResponseEntity<Page<IssueDTO.ResponseDTO>> getCommunity(@RequestParam(name = "order", required = false) String order,
                                                                   @RequestParam(name = "category", required = false) String category,
                                                                   @RequestParam(required = false, defaultValue = "0", name = "pageNo") int pageNo) {
        log.info("/solvedissue ,  order: " + order +  ", pageNo : " + pageNo + ", category : " + category);

        return ResponseEntity.ok(communityService.getSolvedIssueList(order, pageNo)); // 없을 시 null 을 전달함
    }

    @GetMapping(value = "/detail/{issueId}")
    public ResponseEntity<IssueDTO.ResponseDTO> getIssueDetail(@PathVariable(value = "issueId") long issueId,
                                                               @AuthenticationPrincipal NowUserDetails nowUserDetails) {
        log.info("@@@@@@@@@@@@@@ nowUserDetails.getUser().getId() :" + nowUserDetails);

        IssueDTO.ResponseDTO issueDto = null;
        if( nowUserDetails == null ) {
            issueDto = communityService.detailSolvedIssue(issueId, null);
        } else {
            // 사용자가 url로 다른 비공개이거나 포기인 issue 가져올 경우
            issueDto =  communityService.detailSolvedIssue(issueId, nowUserDetails.getUser().getId());
            log.info("issueDto is null ? : " + issueDto);

            if (issueDto == null) {
                throw new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND);
            }
        }
        return ResponseEntity.ok(issueDto);
    }
    //포기한 이슈
    @GetMapping(value = "/giveup/{issueId}")
    public ResponseEntity<IssueDTO.ResponseDTO> getGivenUpIssue(@PathVariable(value = "issueId") long issueId,
                                                                @AuthenticationPrincipal NowUserDetails nowUserDetails){
        Long usersId = nowUserDetails.getUser().getId();
        log.info("포기 유저 아이디 {}", usersId);
        return ResponseEntity.ok(communityService.detailGivenUpIssue(issueId, usersId));

    }

    @Transactional
    @DeleteMapping(value = "likes/{issueId}/{usersId}")
    public ResponseEntity<String> deleteLike(@PathVariable(value = "issueId") long issueId,
                                             @AuthenticationPrincipal NowUserDetails nowUserDetails,
                                             @PathVariable(value = "usersId") long usersId) {
        Long tokenUserId = nowUserDetails.getUser().getId();
        if(!Objects.equals(usersId, tokenUserId)) { // 사용자가 일치하지 않을경우
            throw new BusinessLogicException(ExceptionCode.USER_NOT_FOUND);
        }
        likesService.selectLikes(tokenUserId, issueId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("좋아요 취소가 되었습니다.");
    }

    @GetMapping(value = "/solvedissue/search")
    public ResponseEntity<Page<IssueDTO.ResponseDTO>> search(@RequestParam(value = "title") String title,
                                                             @RequestParam(required = false, defaultValue = "0", name = "pageNo") int pageNo,
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

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteMyComment(@PathVariable("commentId") Long commentId,
                                             @AuthenticationPrincipal NowUserDetails nowUserDetails){
        Long usersId = nowUserDetails.getId();
        mypageService.deleteMyComment(commentId, usersId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("댓글이 삭제되었습니다.");
    }
}


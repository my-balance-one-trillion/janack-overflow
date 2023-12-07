package com.example.janackoverflow.community.controller;

import com.example.janackoverflow.community.entity.Likes;
import com.example.janackoverflow.community.service.CommunityService;
import com.example.janackoverflow.community.service.LikesService;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommunityController {
    private final CommunityService communityService;
    private final LikesService likesService;
    public CommunityController(CommunityService communityService, LikesService likesService) {
        this.communityService = communityService;
        this.likesService = likesService;
    }

    @PostMapping("/{issueId}/likes")
    public ResponseEntity<Likes> createLikes(@RequestBody Users users, @RequestBody Issue issue) {
//        likesService.saveLikes();

        return null;
    }

    @GetMapping(value = "/commentlist")
//    @RequestParam("issue")
    public ResponseEntity<String> getCommunity(Pageable pageable) {
        communityService.getCommentList(1L);
        likesService.getIssueLikes(1L);

        return ResponseEntity.ok().build();
    }
}


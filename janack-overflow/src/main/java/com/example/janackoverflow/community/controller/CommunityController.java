package com.example.janackoverflow.community.controller;

import com.example.janackoverflow.community.service.CommunityService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/commentlist")
    public ResponseEntity<String> community(Pageable pageable) {
        communityService.getCommentList(1L);

        return ResponseEntity.ok().build();
    }
}

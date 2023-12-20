package com.example.janackoverflow.community.repository;

import com.example.janackoverflow.community.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentListRespository {

    Page<Comment> selectCommentList(long issueId, Pageable pageable);
}

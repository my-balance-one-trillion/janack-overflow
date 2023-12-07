package com.example.janackoverflow.community.repository;

import com.example.janackoverflow.community.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    public Page<Comment> findAllByIssue_IdOrderByCreatedAtDesc(Long id, Pageable pageable);
}

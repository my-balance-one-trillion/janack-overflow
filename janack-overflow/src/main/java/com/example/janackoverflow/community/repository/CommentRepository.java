package com.example.janackoverflow.community.repository;

import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Integer countByUsers_id(Long usersId);
    public Page<Comment> findAllByIssue_IdOrderByCreatedAtDesc(Long id, Pageable pageable);
    public Page<Comment> findAllByUsers_IdOrderByCreatedAtDesc(Long id, Pageable pageable);
    void deleteByIdAndUsers_Id(Long id, Long usersId);
}

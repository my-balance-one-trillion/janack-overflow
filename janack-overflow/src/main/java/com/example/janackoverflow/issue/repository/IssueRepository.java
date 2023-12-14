package com.example.janackoverflow.issue.repository;

import com.example.janackoverflow.issue.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.janackoverflow.saving.entity.InputAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Object> findByUsersIdAndStatus(Long userId, String status);
    public Page<Issue> findByUsers_id(Long id, Pageable pageble);


    List<Issue> findByUsersIdAndCreatedAtAfterOrderByCreatedAtDesc(Long userId, LocalDateTime aWeekAgo);
    Long countByUsersIdAndCreatedAtAfter(Long userId, LocalDateTime createAt);

    List<Issue> findTop10ByOrderByCreatedAtDesc();
    List<Issue> findByUsersId(Long userId);
}

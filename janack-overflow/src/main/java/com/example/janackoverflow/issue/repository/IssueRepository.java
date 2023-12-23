package com.example.janackoverflow.issue.repository;

import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.issue.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.janackoverflow.saving.entity.InputAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findByUsersIdAndStatus(Long userId, String status);
    Page<Issue> findByOrderByCreatedAtDesc(Pageable pageble);
    Integer countByUsers_id(Long usersId);
    Page<Issue> findAllByUsers_idOrderByCreatedAtDesc(Long id, Pageable pageble);
    Page<Issue> findByPublicStatusAndStatusOrderByCreatedAtDesc(Boolean publicStatus, String status, Pageable pageable);
    List<Issue> findByUsersIdAndCreatedAtAfterOrderByCreatedAtDesc(Long userId, LocalDateTime aWeekAgo);
    Long countByUsersIdAndInputAccountIdAndStatus(Long userId, Long InputAccountId, String status);
    List<Issue> findTop10ByPublicStatusOrderByCreatedAtDesc(boolean publicStatus);
    List<Issue> findByUsers_IdAndStatus(Long userId, String status);
}

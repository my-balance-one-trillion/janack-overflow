package com.example.janackoverflow.issue.repository;

import com.example.janackoverflow.issue.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Object> findByUsersIdAndStatus(Long userId, String status);
}

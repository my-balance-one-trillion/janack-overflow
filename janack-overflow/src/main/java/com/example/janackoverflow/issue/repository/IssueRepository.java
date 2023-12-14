package com.example.janackoverflow.issue.repository;

import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.saving.entity.InputAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Object> findByUsersIdAndStatus(Long userId, String status);

    List<Issue> findByUsersId(Long userId);
}

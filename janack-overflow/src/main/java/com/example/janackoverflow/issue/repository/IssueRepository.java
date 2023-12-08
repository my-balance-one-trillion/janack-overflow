package com.example.janackoverflow.issue.repository;

import com.example.janackoverflow.issue.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}

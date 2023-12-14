package com.example.janackoverflow.issue.repository;

import com.example.janackoverflow.issue.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    Optional<Solution> findByIssue_Id(long issueId);
}

package com.example.janackoverflow.issue.repository;

import com.example.janackoverflow.issue.domain.SolutionDTO;
import com.example.janackoverflow.issue.domain.response.SolutionResponseDTO;
import com.example.janackoverflow.issue.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    List<Solution> findAllByIssueIdOrderByCreatedAtDesc(Long issueId);
    Optional<Solution> findByIssue_Id(long issueId);
}

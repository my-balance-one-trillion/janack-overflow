package com.example.janackoverflow.issue.repository;

import com.example.janackoverflow.issue.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    Optional<Solution> findByIssue_Id(long issueId);
    List<Solution> findByIssueId(Long id);
    @Query("select month(s.createdAt) as month, count(s) as count from Solution s where s.createdAt between :startDate and :endDate group by month(s.createdAt) order by month(s.createdAt) desc")
    List<Object[]> countSolutionByMonthLast6Months(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}

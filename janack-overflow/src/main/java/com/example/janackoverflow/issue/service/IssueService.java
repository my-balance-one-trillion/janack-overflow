package com.example.janackoverflow.issue.service;

import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class IssueService {
    private final IssueRepository issueRepository;

    public IssueService (IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Issue getIssue(Long issueId) {
        return issueRepository.findById(issueId).get();
    }

    // 에러 등록
    @Transactional
    public Issue createIssue(IssueDTO.RequestDTO issueRequestDTO, Users users) {
        return issueRepository.save(issueRequestDTO.toEntity(users));
    }

    // 에러 등록 시간 조회
    @Transactional(readOnly = true)
    public LocalDateTime getIssueCreatedAt(Long issueId) {
        Optional<Issue> createAt = issueRepository.findById(issueId);
        return createAt.map(Issue::getCreatedAt).orElse(null);
    }
}

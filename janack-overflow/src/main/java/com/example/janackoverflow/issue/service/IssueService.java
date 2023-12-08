package com.example.janackoverflow.issue.service;

import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import org.springframework.stereotype.Service;

@Service
public class IssueService {
    private final IssueRepository issueRepository;

    public IssueService (IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Issue getIssue(Long issueId) {
        return issueRepository.findById(issueId).get();
    }
}

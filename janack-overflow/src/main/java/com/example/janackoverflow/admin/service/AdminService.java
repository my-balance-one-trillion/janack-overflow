package com.example.janackoverflow.admin.service;

import com.example.janackoverflow.community.repository.CommentRepository;
import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.global.pagination.PageResponseDTO;
import com.example.janackoverflow.global.pagination.PaginationService;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.issue.repository.SolutionRepository;
import com.example.janackoverflow.mypage.domain.response.MyIssueResponseDTO;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminService {
    private final UsersRepository usersRepository;
    private final PaginationService paginationService;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final SolutionRepository solutionRepository;

    public AdminService(UsersRepository usersRepository, PaginationService paginationService,
                        IssueRepository issueRepository,
                        CommentRepository commentRepository,
                        SolutionRepository solutionRepository) {
        this.usersRepository = usersRepository;
        this.paginationService = paginationService;
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.solutionRepository = solutionRepository;
    }

    //    유저 전체보기 -> 유저를 다 불러온다->그러려면 유저 객체를 아니 dto를 받아와서 생성해야한다
//    합당한 repo메서드를 만들었으므로 서비스 메서드를 만들자
    public PageResponseDTO<UsersResponseDTO> readAllUsers(Pageable pageable) {
        Page<Users> allUsers = usersRepository.findByOrderByCreatedAtDesc(pageable);
        List<UsersResponseDTO> allUsersList = allUsers.stream().map(users -> UsersResponseDTO.builder()
                .id(users.getId())
                .email(users.getEmail())
                .nickname(users.getNickname())
                .createdAt(users.getCreatedAt())
                .role(users.getRole())
                .status(users.getStatus())
                .build()).toList();
        List<Integer> pageNumber = paginationService.getPaginationPageNumber(allUsers.getNumber(),
                allUsers.getTotalPages());
        return new PageResponseDTO<>(allUsersList, allUsers, pageNumber);
    }

    //유저 상태 변경이므로 이미지 바꾸는거랑 같은 로직이면 될듯
    public void updateUsersStatus(UsersRequestDTO usersRequestDTO, Long usersId) {
        Users users = usersRepository.findById(usersId).orElseThrow(()-> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Users updatedUsers = users.toBuilder()
                .status(usersRequestDTO.getStatus())
                .build();
        usersRepository.save(updatedUsers);
    }

    //사이트 전체글 보기
    public PageResponseDTO<MyIssueResponseDTO> readAllIssue(Pageable pageable) {
        Page<Issue> allIssue = issueRepository.findByOrderByCreatedAtDesc(pageable);
        List<MyIssueResponseDTO> allIlssueList = allIssue.stream().map(issue -> MyIssueResponseDTO.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .users(issue.getUsers())
                .createdAt(issue.getCreatedAt())
                .category(issue.getCategory())
                .publicStatus(issue.isPublicStatus())
                .build()).toList();
        List<Integer> pageNumber = paginationService.getPaginationPageNumber(allIssue.getNumber(), allIssue.getTotalPages());
        return new PageResponseDTO<>(allIlssueList, allIssue, pageNumber);
    }

    //글 권한 수정하기
    public void updateIssuePub(Long issueId, boolean issuePub) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(()-> new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND));
        issue.updatePublicStatus(issuePub);
        issueRepository.save(issue);
    }

    //    각종 수치
    public List<Long> readAllCount() {
        List<Long> count = Stream.of(
                usersRepository.count(),
                issueRepository.count(),
                commentRepository.count()
        ).collect(Collectors.toList());
        return count;
    }

    //    6개월간 가입자 추이
    public List<Map<String, Object>> readSign6Month() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(6);

        List<Object[]> userCountsByMonth = usersRepository.countUsersByMonthLast6Months(startDate, endDate);

        List<Map<String, Object>> signList = userCountsByMonth.stream()
                .map(row -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("month", row[0]);
                    data.put("userCount", ((Number) row[1]).intValue());
                    return data;
                })
                .collect(Collectors.toList());
        return signList;
    }

//    6개월간 해결된 이슈
    public List<Map<String, Object>> readSolution6Month(){
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(6);

        List<Object[]> solutionCountsByMonth = solutionRepository.countSolutionByMonthLast6Months(startDate, endDate);
        List<Map<String, Object>> solutionList = solutionCountsByMonth.stream()
                .map(row -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("month", row[0]);
                    data.put("count", ((Number) row[1]).intValue());
                    return data;
                })
                .collect(Collectors.toList());
        return solutionList;
    }
}

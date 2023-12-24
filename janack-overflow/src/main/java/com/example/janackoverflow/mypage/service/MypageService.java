package com.example.janackoverflow.mypage.service;

import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.repository.CommentRepository;
import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.global.pagination.PageResponseDTO;
import com.example.janackoverflow.global.pagination.PaginationService;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.mypage.domain.response.MyCommentResponseDTO;
import com.example.janackoverflow.mypage.domain.response.MyIssueResponseDTO;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class MypageService {
    private final UsersRepository usersRepository;
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaginationService paginationService;

    public MypageService(UsersRepository usersRepository,
                         CommentRepository commentRepository,
                         IssueRepository issueRepository,
                         PasswordEncoder passwordEncoder,
                         PaginationService paginationService){
        this.usersRepository = usersRepository;
        this.commentRepository =commentRepository;
        this.issueRepository =  issueRepository;
        this.passwordEncoder =  passwordEncoder;
        this.paginationService = paginationService;
    }
//    회원 추가 정보
    public List<Integer> readMyCount(Long usersId){
        List<Integer> myCountList = new ArrayList<>();
        myCountList.add(issueRepository.countByUsers_id(usersId));
        myCountList.add(commentRepository.countByUsers_id(usersId));
        return myCountList;
    }

//    회원 정보 수정
    public String updateUser(UsersRequestDTO usersRequestDTO, Long usersId){
        String updateStatus = "정상적으로 회원정보 수정";
        Users users = usersRepository.findById(usersId).orElseThrow(()->new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        if(passwordEncoder.matches(usersRequestDTO.getPassword(), users.getPassword())){
            Users updatedUser = users.toBuilder()
                    .digit(usersRequestDTO.getDigit())
                    .birth(usersRequestDTO.getBirth())
                    .name(usersRequestDTO.getName())
                    .nickname(usersRequestDTO.getNickname())
                    .holder(usersRequestDTO.getHolder())
                    .bankName(usersRequestDTO.getBankName())
                    .outputAcntNum(usersRequestDTO.getOutputAcntNum())
                    .build();
            if(StringUtils.isNotEmpty(usersRequestDTO.getNewPassword())){
                if(usersRequestDTO.getNewPassword().equals(usersRequestDTO.getNewPasswordConfirm())){
                    usersRepository.save(updatedUser);
                    updatedUser.updatePassword(passwordEncoder.encode(usersRequestDTO.getNewPassword()));
                }else{
                    updateStatus = "새 패스워드 재검증 오류";
                    return updateStatus;
                }
            }
            usersRepository.save(updatedUser);
        }else{
            updateStatus = "패스워드 오류";
        }
        return updateStatus;
    }

//    프로필사진만 교체
    public void updateProfileImage(UsersRequestDTO usersRequestDTO, Long usersId){
        Users users = usersRepository.findById(usersId).orElseThrow(()-> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Users updatedUsers = users.toBuilder()
                .profileImage(usersRequestDTO.getProfileImage())
                .build();
        usersRepository.save(updatedUsers);
    }

//    내가 쓴글 보기
    public PageResponseDTO<MyIssueResponseDTO> readMyIssue(Long usersId, Pageable pageable){
        Page<Issue> myIssueList = issueRepository.findAllByUsers_idOrderByCreatedAtDesc(usersId,pageable);
        List<MyIssueResponseDTO> issueResponseDtolist = myIssueList.stream().map(issue -> MyIssueResponseDTO.builder()
                .title(issue.getTitle())
                .category(issue.getCategory())
                .createdAt(issue.getCreatedAt())
                .status(issue.getStatus())
                .views(issue.getViews())
                .build()).toList();
        List<Integer> pageNumber = paginationService.getPaginationPageNumber(myIssueList.getNumber(),
                myIssueList.getTotalPages());
        return new PageResponseDTO<>(issueResponseDtolist, myIssueList, pageNumber);
    }

//    내가 쓴 댓글 보기
    public PageResponseDTO<MyCommentResponseDTO> readMyComment(Long usersId, Pageable pageable){
        Page<Comment> myCommentList = commentRepository.findAllByUsers_IdOrderByCreatedAtDesc(usersId,pageable);
        // 댓글DtoList
        List<MyCommentResponseDTO> commentResponseDtolist = myCommentList.stream().map(comment -> MyCommentResponseDTO.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .issue_title(comment.getIssue().getTitle())
                .createdAt(comment.getCreatedAt())
                .build()).toList();
        List<Integer> pageNumber = paginationService.getPaginationPageNumber(myCommentList.getNumber(), myCommentList.getTotalPages());
        return new PageResponseDTO<>(commentResponseDtolist, myCommentList, pageNumber);
    }
//    댓글 삭제
    @Transactional
    public void deleteMyComment(Long commentId,Long usersId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(RuntimeException::new);
        if(Objects.equals(usersId, comment.getUsers().getId())){
            commentRepository.deleteByIdAndUsers_Id(commentId, usersId);
        }else{
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }
}

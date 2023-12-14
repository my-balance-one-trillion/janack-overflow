package com.example.janackoverflow.mypage.service;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.repository.CommentRepository;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.mypage.domain.response.myCommentResponseDTO;
import com.example.janackoverflow.mypage.domain.response.myIssueResponseDTO;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class MypageService {
    private final UsersRepository usersRepository;
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final PasswordEncoder passwordEncoder;

    public MypageService(UsersRepository usersRepository,
                         CommentRepository commentRepository,
                         IssueRepository issueRepository,
                         PasswordEncoder passwordEncoder){
        this.usersRepository = usersRepository;
        this.commentRepository =commentRepository;
        this.issueRepository =  issueRepository;
        this.passwordEncoder =  passwordEncoder;
    }

//    회원 정보 수정
    public void updateUser(UsersRequestDTO usersRequestDTO, Long usersId){
        Users users = usersRepository.findById(usersId).orElseThrow(RuntimeException::new);
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
            if(usersRequestDTO.getNewPassword() != null){
                if(usersRequestDTO.getNewPassword().equals(usersRequestDTO.getNewPasswordConfirm())){
                    updatedUser.updatePassword(usersRequestDTO.getNewPassword());
                }else{
                    throw new RuntimeException("패스워드와 패스워드확인이 다릅니다.");
                }
            }
            usersRepository.save(updatedUser);
        }else{
            throw new RuntimeException("패스워드가 틀렸습니다.");
        }
    }

//    프로필사진만 교체
    public void updateProfileImage(UsersRequestDTO usersRequestDTO, Long usersId){
        Users users = usersRepository.findById(usersId).orElseThrow(RuntimeException::new);
        Users updatedUsers = users.toBuilder()
                .profileImage(usersRequestDTO.getProfileImage())
                .build();
        usersRepository.save(updatedUsers);
    }

//    내가 쓴글 보기
    public Page<myIssueResponseDTO> readMyIssue(Long usersId){
        Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Issue> MyIssueList = issueRepository.findByUsers_id(usersId,pageable);
        List<myIssueResponseDTO> issueResponseDtolist = MyIssueList.stream().map(issue -> myIssueResponseDTO.builder()
                .title(issue.getTitle())
                .category(issue.getCategory())
                .createdAt(issue.getCreatedAt())
                .status(issue.getStatus())
                .views(issue.getViews())
                .build()).toList();
        Page<myIssueResponseDTO> MyIssuePage = new PageImpl<>(issueResponseDtolist, pageable, MyIssueList.getTotalPages());
        return MyIssuePage;
    }

//    내가 쓴 댓글 보기
    public Page<myCommentResponseDTO> readMyComment(Long usersId){
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Comment> myCommentList = commentRepository.findAllByUsers_IdOrderByCreatedAtDesc(usersId,pageable);
        // 댓글DtoList
        List<myCommentResponseDTO> commentResponseDtolist = myCommentList.stream().map(comment -> myCommentResponseDTO.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .issue_title(comment.getIssue().getTitle())
                .createdAt(comment.getCreatedAt())
                .build()).toList();
        Page<myCommentResponseDTO> MyCommentPage = new PageImpl<>(commentResponseDtolist, pageable, myCommentList.getTotalElements());
        return MyCommentPage;
    }
//    댓글 삭제
    @Transactional
    public void deleteMyComment(Long commentId,Long usersId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(RuntimeException::new);
        if(Objects.equals(usersId, comment.getUsers().getId())){
            commentRepository.deleteByIdAndUsers_Id(commentId, usersId);
        }else{
            throw new RuntimeException("잘못된 접근입니다!");
        }
    }
}

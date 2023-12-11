package com.example.janackoverflow.mypage.service;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.repository.CommentRepository;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class MypageService {
    private final UsersRepository usersRepository;
    private final CommentRepository commentRepository;

    public MypageService(UsersRepository usersRepository, CommentRepository commentRepository){
        this.usersRepository = usersRepository;
        this.commentRepository =commentRepository;
    }

//    회원 정보 수정
    public void updateUser(UsersRequestDTO usersRequestDTO, Long usersId){
        Users users = usersRepository.findById(usersId).orElseThrow(RuntimeException::new);
        if(users.getPassword().equals(usersRequestDTO.getPassword())){
            Users updatedUser = users.toBuilder()
                    .email(usersRequestDTO.getEmail())
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

//    내가 쓴 댓글 보기
    public Page<CommentDTO.CommentResponseDto> readMyComment(Long usersId){
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        Page<Comment> myCommentList = commentRepository.findAllByUsers_IdOrderByCreatedAtDesc(usersId,pageable);
        // 댓글DtoList
        List<CommentDTO.CommentResponseDto> commentResponseDtolist = myCommentList.stream().map(comment -> CommentDTO.CommentResponseDto.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .build()).toList();

        Page<CommentDTO.CommentResponseDto> commentPage = new PageImpl<>(commentResponseDtolist, pageable, myCommentList.getTotalElements());
        return commentPage;
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

package com.example.janackoverflow.chat.domain;

import com.example.janackoverflow.chat.entity.ChatMessage;
import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.community.domain.CommunityUsersDTO;
import com.example.janackoverflow.user.entity.Users;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class ChatMessageDTO {
    public enum MessageType{
        ENTER, TALK;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestDTO{
        private MessageType type;
        private String roomId;
        private String content;
        private Long userId;
        private LocalDateTime createdAt;

        public ChatMessage toEntity(Users users, ChatRoom chatRoom){
            return ChatMessage.builder()
                    .type(type)
                    .content(content)
                    .users(users)
                    .chatRoom(chatRoom)
                    .build();
        }

    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDTO{
        private MessageType type;
        private String roomId;
        private String content;
        private CommunityUsersDTO usersDTO;
        private LocalDateTime createdAt;

        public ChatMessageDTO.ResponseDTO fromEntity(ChatMessage chatMessage){
            return ResponseDTO.builder()
                    .type(chatMessage.getType())
                    .roomId(chatMessage.getChatRoom().getRoomId())
                    .content(chatMessage.getContent())
                    .usersDTO(chatMessage.getUsers().toIssueDto())
                    .createdAt(chatMessage.getCreatedAt())
                    .build();

        }
    }



}

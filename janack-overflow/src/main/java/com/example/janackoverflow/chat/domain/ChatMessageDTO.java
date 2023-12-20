package com.example.janackoverflow.chat.domain;

import com.example.janackoverflow.chat.entity.ChatMessage;
import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.community.domain.CommunityUsersDTO;
import com.example.janackoverflow.user.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class ChatMessageDTO {
    public enum MessageType{
        ENTER, TALK, QUIT;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class RequestDTO{
        private MessageType type;
        private Long roomId;
        private String content;
        private Long userId;

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
    @ToString
    public static class ResponseDTO{
        private MessageType type;
        private String roomId;
        private String content;
        private CommunityUsersDTO usersDTO;
        @JsonFormat(pattern = "HH:mm")
        private LocalDateTime createdAt;

        public static ChatMessageDTO.ResponseDTO fromEntity(ChatMessage chatMessage){
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

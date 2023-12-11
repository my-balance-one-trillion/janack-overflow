package com.example.janackoverflow.chat.entity;

import com.example.janackoverflow.chat.config.WebSocketConfig;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    private String roomId;
    private String roomName;
    private long userCount;

    private HashMap<String, String> userList = new HashMap<String, String>();

    public ChatRoom create(String roomName){
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
        .build();

        return chatRoom;
    }
}

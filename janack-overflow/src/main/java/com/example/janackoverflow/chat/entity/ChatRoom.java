package com.example.janackoverflow.chat.entity;

import com.example.janackoverflow.chat.config.WebSocketConfig;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {

    private Long id;

    private String roomName;

    @Builder
    public ChatRoom(String roomName){
        this.roomName = roomName;
    }
}

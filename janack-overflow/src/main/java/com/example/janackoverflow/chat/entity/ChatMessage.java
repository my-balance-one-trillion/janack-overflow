package com.example.janackoverflow.chat.entity;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    public enum MessageType{
        ENTER, TALK, QUIT;
    }
    private MessageType type;
    private int roomId;
    private String sender;
    private String message;
}

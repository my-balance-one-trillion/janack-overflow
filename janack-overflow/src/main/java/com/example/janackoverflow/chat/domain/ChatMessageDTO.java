package com.example.janackoverflow.chat.domain;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    public enum MessageType{
        ENTER, TALK, QUIT;
    }
    private MessageType type;
    private int roomId;
    private String sender;
    private String message;
}

package com.example.janackoverflow.chat.domain;

import com.example.janackoverflow.chat.entity.ChatRoom;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatRoomDTO {
    private String roomId;
    private String roomName;
    private int max;
    private int now;

    public static ChatRoomDTO fromEntity(ChatRoom chatRoom){
        return ChatRoomDTO.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .max(chatRoom.getMax())
                .now(1)
                .build();
    }
}

package com.example.janackoverflow.chat.entity;

import com.example.janackoverflow.chat.config.WebSocketConfig;
import com.example.janackoverflow.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRoom{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomId;

    @Column(nullable = false)
    private String roomName;

    @ColumnDefault(value = "4")
    private int max;//최대 유저수


    //방주인
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

//    public ChatRoom create(String roomName, Users users){
//        ChatRoom chatRoom = ChatRoom.builder()
//                .roomId(UUID.randomUUID().toString())
//                .roomName(roomName)
//                .users(users)
//        .build();
//
//        return chatRoom;
//    }
}

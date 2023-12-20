package com.example.janackoverflow.chat.entity;

import com.example.janackoverflow.chat.config.WebSocketConfig;
import com.example.janackoverflow.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    private String category;

    //유저 리스트
    //id/채팅방id/유저id

    @OneToMany(mappedBy = "chatRoom", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ChatRoomUsers> usersList;

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

package com.example.janackoverflow.chat.entity;

import com.example.janackoverflow.community.domain.CommunityUsersDTO;
import com.example.janackoverflow.user.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    public CommunityUsersDTO toCommunityUsersDTO(){
        return CommunityUsersDTO.builder()
                .id(users.getId())
                .nickname(users.getNickname())
                .profileImage(users.getProfileImage())
                .build();
    }
}

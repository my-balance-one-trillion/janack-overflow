package com.example.janackoverflow.chat.repository;

import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.chat.entity.ChatRoomUsers;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    public List<ChatRoom> findByUsers(Users users);

    public Optional<ChatRoom> findByRoomId(String roomId);


}

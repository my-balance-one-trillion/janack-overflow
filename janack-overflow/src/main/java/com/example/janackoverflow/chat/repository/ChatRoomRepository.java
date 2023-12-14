package com.example.janackoverflow.chat.repository;

import com.example.janackoverflow.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    public Optional<ChatRoom> findByUsersId(Long userId);

    public Optional<ChatRoom> findByRoomId(String roomId);
}

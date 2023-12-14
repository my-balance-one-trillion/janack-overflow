package com.example.janackoverflow.chat.repository;

import com.example.janackoverflow.chat.entity.ChatMessage;
import com.example.janackoverflow.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    //채팅 내역 불러오기
    public List<ChatMessage> findByChatRoomIdOrderByCreatedAtDesc(Long roomId);
}

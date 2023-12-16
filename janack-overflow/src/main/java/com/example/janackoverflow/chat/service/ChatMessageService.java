package com.example.janackoverflow.chat.service;

import com.example.janackoverflow.chat.domain.ChatMessageDTO;
import com.example.janackoverflow.chat.entity.ChatMessage;
import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.chat.repository.ChatMessageRepository;
import com.example.janackoverflow.chat.repository.ChatRoomRepository;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UsersRepository usersRepository;

    //채팅 저장
    public ChatMessage create(ChatMessageDTO.RequestDTO chatMessageDTO){
        Users users = usersRepository.findById(chatMessageDTO.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("없음"));
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDTO.getRoomId())
                        .orElseThrow(() -> new IllegalArgumentException("없음" + chatMessageDTO.getRoomId()));

        ChatMessage chatMessage = chatMessageDTO.toEntity(users, chatRoom);
        chatMessageRepository.save(chatMessage);

        return chatMessage;
    }
    //해당 채팅방 채팅 전체 불러오기
    public List<ChatMessageDTO.ResponseDTO> readAll(Long roomId){
        return chatMessageRepository.findByChatRoomIdOrderByCreatedAt(roomId)
                .stream().map(ChatMessageDTO.ResponseDTO::fromEntity)
                .toList();
    }
    //채팅 삭제


}

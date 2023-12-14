package com.example.janackoverflow.chat.service;

import com.example.janackoverflow.chat.domain.ChatRoomDTO;
import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.chat.repository.ChatRoomRepository;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UsersRepository usersRepository;

    //방 생성
    public ChatRoom create(String roomName, Long userId){
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+userId));

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .users(users)
                .max(4)
                .build();

        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }
    //자기 채팅방 조회
    public ChatRoom read(Long userId){
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+userId));

        ChatRoom chatRoom = chatRoomRepository.findByUsersId(userId)
                .orElseThrow(() -> new IllegalArgumentException("개설한 채팅방 없음"));

        return chatRoom;
    }
    //전체 채팅방 조회
    public List<ChatRoomDTO> readAll(){
        List<ChatRoomDTO> chatRoomList = chatRoomRepository.findAll()
                .stream().map(ChatRoomDTO::fromEntity)
                .toList();

        return chatRoomList;
    }
    //채팅방 삭제
    public void delete(Long chatRoomId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("없음"));
        chatRoomRepository.delete(chatRoom);
    }


}

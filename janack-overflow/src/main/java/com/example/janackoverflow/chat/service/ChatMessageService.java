package com.example.janackoverflow.chat.service;

import com.example.janackoverflow.chat.domain.ChatMessageDTO;
import com.example.janackoverflow.chat.entity.ChatMessage;
import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.chat.repository.ChatMessageRepository;
import com.example.janackoverflow.chat.repository.ChatRoomRepository;
import com.example.janackoverflow.chat.repository.ChatRoomUsersRepository;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ChatRoomUsersRepository chatRoomUsersRepository;

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
    //채팅 내역 불러오기
    public List<ChatMessageDTO.ResponseDTO> readChatMessages(Long roomId, Long usersId){
        Users users = usersRepository.findById(usersId)
                .orElseThrow(() -> new IllegalArgumentException("없음"));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("없음" + roomId));

        List<ChatMessage> chatMessageList;
        System.out.println("유저 상태 체크:"+chatRoom.getUsers().getId() +"/"+users.getId());
        if(chatRoom.getUsers().getId().equals(users.getId())){//방장일 때
            System.out.println("방장임");
            chatMessageList = chatMessageRepository.findByChatRoomIdOrderByCreatedAt(roomId);
        } else if(chatRoomUsersRepository.findByChatRoomAndUsers(chatRoom, users).isPresent()){//방장아닐때
            System.out.println("방장x 유저");
            chatMessageList = readChatSinceJoin(roomId, usersId);
        } else{
            System.out.println("처음 들어옴");
            chatMessageList = new ArrayList<>();
        }
        return chatMessageList
                .stream().map(ChatMessageDTO.ResponseDTO::fromEntity)
                .toList();
    }
    //입장 후 채팅 불러오기
    public List<ChatMessage> readChatSinceJoin(Long roomId, Long usersId){
        ChatMessage chatMessage = chatMessageRepository.findTop1ByChatRoomIdAndUsersIdAndTypeOrderByCreatedAtDesc(roomId, usersId, ChatMessageDTO.MessageType.ENTER)
                .orElseThrow(() -> new IllegalArgumentException("입장한 적 없음"));

        return chatMessageRepository.findByChatRoomIdAndIdGreaterThanEqual(roomId, chatMessage.getId());
    }

    //채팅 삭제


}

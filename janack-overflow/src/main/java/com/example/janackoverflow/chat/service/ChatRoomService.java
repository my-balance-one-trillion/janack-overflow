package com.example.janackoverflow.chat.service;

import com.example.janackoverflow.chat.domain.ChatRoomDTO;
import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.chat.entity.ChatRoomUsers;
import com.example.janackoverflow.chat.repository.ChatRoomRepository;
import com.example.janackoverflow.chat.repository.ChatRoomUsersRepository;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ChatRoomUsersRepository chatRoomUsersRepository;

    //방 생성
    public ChatRoom create(ChatRoomDTO.RequestDTO chatRoomDTO, Long userId){

        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+userId));
        //유저 리스트
        List<ChatRoomUsers> usersList = new ArrayList<>();


        String roomId = UUID.randomUUID().toString();

        ChatRoom chatRoom = chatRoomDTO.toEntity(users, roomId, usersList);

        chatRoomRepository.save(chatRoom);

        ChatRoomUsers chatRoomUsers = ChatRoomUsers.builder()
                .chatRoom(chatRoom)
                .users(users)
                .build();

        chatRoomUsersRepository.save(chatRoomUsers);

        return chatRoom;
    }
    //채팅방 단건 조회
    public ChatRoomDTO.ResponseDTO read(Long id){

        ChatRoom chatRoom = chatRoomRepository.findById(id)
                .orElseThrow(() ->  new IllegalArgumentException("채팅방 없음"));

        return ChatRoomDTO.ResponseDTO.fromEntity(chatRoom);
    }
    //자기 채팅방 조회
    public List<ChatRoomDTO.ResponseDTO> readMyChatRooms(Long userId){

        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+userId));


        //chatroom-userslist에 포함되어 있는지 확인
        List<ChatRoomDTO.ResponseDTO> chatRoomList = chatRoomUsersRepository.findChatRoomByUsers(users)
                .stream().map(ChatRoomDTO.ResponseDTO::fromEntity)
                .toList();
        return chatRoomList;
    }
    //전체 채팅방 조회
    public List<ChatRoomDTO.ResponseDTO> readAll(){
        List<ChatRoomDTO.ResponseDTO> chatRoomList = chatRoomRepository.findAll()
                .stream().map(ChatRoomDTO.ResponseDTO::fromEntity)
                .toList();

        return chatRoomList;
    }
    //채팅방 삭제
    public Boolean delete(Long chatRoomId, Long userId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("없음"));
        //방주인과 로그인 유저 동일시
        if(chatRoom.getUsers().getId().equals(userId)){
            chatRoomRepository.delete(chatRoom);
            return true;
        } else {
            return false;
        }

    }
    //사람 입장
    @Transactional
    public void enterChatRoom(Long chatRoomId, Long userId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("없음"));
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+userId));



        if( chatRoomUsersRepository.findByChatRoomAndUsers(chatRoom, users).isPresent()){
            System.out.println("이미 있는 유저거나 다참");

        } else {
            chatRoomUsersRepository.save(ChatRoomUsers.builder()
                    .chatRoom(chatRoom)
                    .users(users)
                    .build());

        }
    }

    //사람 퇴장
    public void quitChatRoom(Long chatRoomId, Long userId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("없음"));
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+userId));
        ChatRoomUsers chatRoomUsers = chatRoomUsersRepository.findByChatRoomAndUsers(chatRoom, users)
                .orElseThrow(() -> new IllegalArgumentException("x"));
        System.out.println("퇴장:"+chatRoomUsers.getId());
        chatRoomUsersRepository.delete(chatRoomUsers);
    }
    //최대 인원수 체크
    public boolean isMax(Long chatRoomId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("없음"));
        return chatRoom.getUsersList().size()>=chatRoom.getMax();
    }

}

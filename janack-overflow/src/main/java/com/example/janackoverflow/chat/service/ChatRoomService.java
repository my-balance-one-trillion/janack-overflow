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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public ChatRoom create(ChatRoomDTO.RequestDTO chatRoomDTO, Long usersId){

        Users users = usersRepository.findById(usersId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+usersId));
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
    //내가 속한 방 조회
    public List<ChatRoomDTO.ResponseDTO> readJoinedRooms(Long usersId){

        Users users = usersRepository.findById(usersId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+usersId));


        //chatroom-userslist에 포함되어 있는지 확인
        List<ChatRoomDTO.ResponseDTO> chatRoomList = chatRoomUsersRepository.findChatRoomByUsers(users)
                .stream().map(ChatRoomDTO.ResponseDTO::fromEntity)
                .toList();

        return chatRoomList;
    }
    //내가 만든 방 조회
    public List<ChatRoomDTO.ResponseDTO> readCreatedRoom(Long usersId){
        Users users = usersRepository.findById(usersId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+usersId));

        List<ChatRoomDTO.ResponseDTO> chatRoomList = chatRoomRepository.findByUsers(users)
                .stream().map(ChatRoomDTO.ResponseDTO::fromEntity)
                .toList();

        return chatRoomList;
    }
    //전체 채팅방 조회
    public List<ChatRoomDTO.ResponseDTO> readAll(){
        List<ChatRoomDTO.ResponseDTO> chatRoomList = chatRoomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream().map(ChatRoomDTO.ResponseDTO::fromEntity)
                .toList();

        return chatRoomList;
    }
    //채팅방 삭제
    public Boolean delete(Long chatRoomId, Long usersId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("없음"));
        //방주인과 로그인 유저 동일시
        if(chatRoom.getUsers().getId().equals(usersId)){
            chatRoomRepository.delete(chatRoom);
            return true;
        } else {
            return false;
        }

    }
    //사람 입장
    @Transactional
    public void enterChatRoom(Long chatRoomId, Long usersId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("채팅방 없음"));
        Users users = usersRepository.findById(usersId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+usersId));

        if( !isJoined(chatRoomId, usersId) && isMax(chatRoomId)){
            //없는데 꽉참
            throw new IllegalArgumentException("인원이 다 찼습니다.");
        } else if(isJoined(chatRoomId, usersId)) {
            System.out.println("이미 들어와있음");
        }  else {
            chatRoomUsersRepository.save(ChatRoomUsers.builder()
                    .chatRoom(chatRoom)
                    .users(users)
                    .build());

        }
    }

    //사람 퇴장
    public void quitChatRoom(Long chatRoomId, Long usersId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        Users users = usersRepository.findById(usersId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        ChatRoomUsers chatRoomUsers = chatRoomUsersRepository.findByChatRoomAndUsers(chatRoom, users)
                .orElseThrow(() -> new IllegalArgumentException("들어와있지 않습니다."));

        System.out.println("퇴장:"+chatRoomUsers.getId());

        chatRoomUsersRepository.delete(chatRoomUsers);
    }
    //최대 인원수 체크
    public boolean isMax(Long chatRoomId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("없음"));
        return chatRoom.getUsersList().size()>=chatRoom.getMax();
    }
    //들어와있는지 체크
    public boolean isJoined(Long chatRoomId, Long usersId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new IllegalArgumentException("없음"));
        Users users = usersRepository.findById(usersId)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+usersId));

        //유저 유무
        return chatRoomUsersRepository.findByChatRoomAndUsers(chatRoom, users).isPresent();

    }

}

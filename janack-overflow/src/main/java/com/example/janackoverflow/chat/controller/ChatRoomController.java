package com.example.janackoverflow.chat.controller;

import com.example.janackoverflow.chat.domain.ChatRoomDTO;
import com.example.janackoverflow.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    //모든 채팅방 리스트
    @GetMapping
    public ResponseEntity<?> getAllChatRooms(){
        List<ChatRoomDTO> chatRoomDTOList = chatRoomService.readAll();
        return ResponseEntity.ok(chatRoomDTOList);
    }
}

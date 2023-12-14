package com.example.janackoverflow.chat.controller;

import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/chatList")
    public ResponseEntity<?> chatList(){
        List<ChatRoom> chatRoomList = chatService.findAllRoom();
        return ResponseEntity.ok().body(chatRoomList);
    }
//    @PostMapping
//    public ResponseEntity<?> createRoom(){
//
//    }

}

package com.example.janackoverflow.chat.controller;

import com.example.janackoverflow.chat.domain.ChatMessageDTO;
import com.example.janackoverflow.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/message")
public class ChatMessageController {
    @Autowired
    private ChatMessageService chatMessageService;

    //방 채팅 불러오기
    @GetMapping("/{roomId}")
    public ResponseEntity<?> chatList(@PathVariable(value = "roomId") Long roomId){
        List<ChatMessageDTO.ResponseDTO> chatRoomList = chatMessageService.readAll(roomId);
        return ResponseEntity.ok(chatRoomList);
    }
//    @PostMapping
//    public ResponseEntity<?> createRoom(){
//
//    }

}

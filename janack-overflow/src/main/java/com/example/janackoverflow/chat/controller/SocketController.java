package com.example.janackoverflow.chat.controller;

import com.example.janackoverflow.chat.domain.ChatMessageDTO;
import com.example.janackoverflow.chat.entity.ChatMessage;
import com.example.janackoverflow.chat.service.ChatMessageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SocketController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketController.class);

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    //방id, 유저 리스트
    private final Map<Long, List<Long>> userList = new HashMap<>();

    @Autowired
    private ChatMessageService chatMessageService;



    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event){
        LOGGER.info("Received a new web socket connection입니다. {}", event);

    }
    @EventListener
    public void handleWebSocketDisconnectListender(SessionConnectedEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        LOGGER.info("sessionID Disconnected:" + sessionId);
    }
    @MessageMapping("/cache/{roomId}")
    @SendTo("/sub/cache/{roomId}")
    public void sendMessage(@DestinationVariable("roomId") String roomId, ChatMessageDTO.RequestDTO chat){
        System.out.println("실행됨"+chat);
        ChatMessage chatMessage = chatMessageService.create(chat);
        ChatMessageDTO.ResponseDTO responseDTO = ChatMessageDTO.ResponseDTO.fromEntity(chatMessage);

        simpMessageSendingOperations.convertAndSend("/sub/cache/"+roomId, responseDTO);
    }
}

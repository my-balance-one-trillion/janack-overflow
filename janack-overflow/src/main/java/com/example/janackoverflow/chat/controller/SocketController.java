package com.example.janackoverflow.chat.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SocketController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketController.class);

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event){
        LOGGER.info("Received a new web socket connection");
    }
    @EventListener
    public void handleWebSocketDisconnectListender(SessionConnectedEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        LOGGER.info("sessionID Disconnected:" + sessionId);
    }
    @MessageMapping("/cache")
    @SendTo("/sub/cache")
    public void sendMessage(Map<String, Object> params){
        System.out.println("실행됨");
        System.out.println("params:"+params+"\ndestination:"+"/sub/cache/"+params.get("channelId"));
        simpMessageSendingOperations.convertAndSend("/sub/cache/"+params.get("channelId"), params);
    }
}

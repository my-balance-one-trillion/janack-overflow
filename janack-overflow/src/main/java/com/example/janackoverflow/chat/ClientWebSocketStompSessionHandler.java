package com.example.janackoverflow.chat;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ClientWebSocketStompSessionHandler extends StompSessionHandlerAdapter {

    @Override
    public void handleFrame(StompHeaders headers, Object payload){
        //구독한 채널의 메세지 받기
        System.out.println("SpringStompSessionHandler.handleFrame");
        System.out.println("headers = " + headers);
        System.out.println("payload = " + new String((byte[]) payload));
    }
    @Override
    public Type getPayloadType(StompHeaders headers){
        return Object.class;
    }
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders){
        session.subscribe("/sub/cache/karim", this);

        Map<String, Object> params = new HashMap<>();
        params.put("channelId", "karim");
        System.out.println("params = " + params);
    }
    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception){
        System.out.println("SpringStompSessionHandler.handleException");
        System.out.println("exception = " + exception);
    }
    @Override
    public void handleTransportError(StompSession session, Throwable exception){
        System.out.println("SpringStompSessionHandler.handleTransportError");
    }
}

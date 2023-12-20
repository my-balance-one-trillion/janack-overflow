package com.example.janackoverflow.chat;

import com.example.janackoverflow.chat.domain.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class StompHandler implements ChannelInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StompHandler.class);

//    @Override
//    public Message<?> preSend(Message message, MessageChannel channel){
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        if
//        return message;
//    }
    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        System.out.println("message:"+message+"\nchannel:"+channel+"\nsent:"+sent);
        switch (accessor.getCommand()){
            case CONNECT:
                LOGGER.info("세션 들어옴 => {}", sessionId);
                break;
            case DISCONNECT:
                LOGGER.info("세션 끊음 => {}", sessionId);
                break;
            case UNSUBSCRIBE:
                LOGGER.info("구독 끊음 => {}", sessionId);
                break;
            default:
                break;
        }

    }

}

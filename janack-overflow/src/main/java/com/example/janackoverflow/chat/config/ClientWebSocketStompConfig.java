package com.example.janackoverflow.chat.config;

import com.example.janackoverflow.chat.ClientWebSocketStompSessionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class ClientWebSocketStompConfig {
    @Bean
    public WebSocketStompClient WebSocketStompClient(WebSocketStompClient webSocketStompClient,
                                                     StompSessionHandler stompSessionHandler){
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("host", "karim");
        System.out.println("WebSocketStompClient");
        Object[] urlVariables = {};
        String url = "ws://localhost:8081/ws";
        webSocketStompClient.connectAsync(url, null, stompHeaders, stompSessionHandler, urlVariables);

        return webSocketStompClient;
    }

    @Bean
    public WebSocketStompClient webSocketStompClient(){
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        return new WebSocketStompClient(webSocketClient);
    }
    @Bean
    public StompSessionHandler stompSessionHandler(){
        return new ClientWebSocketStompSessionHandler();
    }
}

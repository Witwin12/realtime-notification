package com.example.realtime_notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        // ท่อสำหรับส่งข้อมูลออกไปหา Client (คนรับรอฟังที่ /topic)
        config.enableSimpleBroker("/topic");
        // หัวข้อที่ Client จะส่งข้อมูลกลับมาหา Server
        config.setApplicationDestinationPrefixes("/app");

    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        // จุดเชื่อมต่อครั้งแรก (Handshake) ให้ Client ต่อมาที่นี่
        registry.addEndpoint("/ws-notification").withSockJS();
    }
}

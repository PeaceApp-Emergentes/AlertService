package com.upc.pre.peaceapp.alerts.infrastructure.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final EmergencyAlertWebSocketHandler emergencyAlertWebSocketHandler;

    public WebSocketConfig(EmergencyAlertWebSocketHandler emergencyAlertWebSocketHandler) {
        this.emergencyAlertWebSocketHandler = emergencyAlertWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(emergencyAlertWebSocketHandler, "/ws/alerts")
                .setAllowedOriginPatterns("*");
    }
}

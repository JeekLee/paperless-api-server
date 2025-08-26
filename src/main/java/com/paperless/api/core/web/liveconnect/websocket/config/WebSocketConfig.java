package com.paperless.api.core.web.liveconnect.websocket.config;

import com.paperless.api.core.web.liveconnect.websocket.interceptor.JwtHandshakeInterceptor;
import com.paperless.api.core.web.liveconnect.websocket.interceptor.AuthChannelInterceptor;
import com.paperless.api.core.web.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final CorsProperties corsProperties;
    private final JwtHandshakeInterceptor handshakeInterceptor;
    private final AuthChannelInterceptor authChannelInterceptor;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/publish");
        config.setUserDestinationPrefix("/member");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns(corsProperties.getAllowedOrigins())
                .addInterceptors(handshakeInterceptor);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor);
    }
}

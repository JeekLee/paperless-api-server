package com.paperless.api.core.web.liveconnect.websocket.interceptor;

import com.paperless.api.authentication.dto.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getCommand() != null) {
            UserPrincipal userPrincipal = (UserPrincipal) accessor.getSessionAttributes().get("USER_PRINCIPAL");
            accessor.setUser(userPrincipal);
        }

        return message;
    }
}

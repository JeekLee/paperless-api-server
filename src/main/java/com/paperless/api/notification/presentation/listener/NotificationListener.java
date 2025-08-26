package com.paperless.api.notification.presentation.listener;

import com.paperless.api.notification.dto.NotificationMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationListener implements MessageListener {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            NotificationMessage notificationMessage = objectMapper.readValue(new String(message.getBody()), NotificationMessage.class);

            simpMessagingTemplate.convertAndSendToUser(
                    String.valueOf(notificationMessage.getMemberId()),
                    "/queue/notification",
                    notificationMessage
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

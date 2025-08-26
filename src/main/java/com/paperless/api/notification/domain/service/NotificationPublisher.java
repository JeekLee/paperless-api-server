package com.paperless.api.notification.domain.service;

import com.paperless.api.notification.dto.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(NotificationMessage message) {
        redisTemplate.convertAndSend("member:" + message.getMemberId() + ":notification", message);
    }
}

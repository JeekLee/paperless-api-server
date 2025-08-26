package com.paperless.api.notification.application.service;

import com.paperless.api.core.error.ExceptionCreator;
import com.paperless.api.member.application.service.MemberService;
import com.paperless.api.member.domain.model.Member;
import com.paperless.api.notification.application.repository.NotificationRepository;
import com.paperless.api.notification.domain.model.Notification;
import com.paperless.api.notification.domain.service.NotificationPublisher;
import com.paperless.api.notification.dto.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.paperless.api.notification.exception.NotificationException.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationPublisher notificationPublisher;
    private final NotificationRepository notificationRepository;

    private final MemberService memberService;

    @Transactional
    public void sendNotification(Long memberId, String message, String redirectPath) {
        Member member = memberService.getMember(memberId);

        Notification notification = Notification.create(memberId, message, redirectPath);

        notification = notificationRepository.save(notification);

        NotificationMessage notificationMessage = NotificationMessage.builder()
                .memberId(member.id())
                .notificationId(notification.id())
                .message(notification.message())
                .build();

        notificationPublisher.publish(notificationMessage);
    }

    @Transactional
    public void readNotification(Long memberId, Long notificationId) {
        Notification notification = notificationRepository.findByMemberIdAndId(memberId, notificationId)
                .orElseThrow(() -> ExceptionCreator.create(NOT_FOUND, "memberId: " + memberId + " / notificationId: " + notificationId));

        notification = notification.read();
        notificationRepository.save(notification);
    }
}

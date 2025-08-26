package com.paperless.api.notification.presentation.api;

import com.paperless.api.notification.presentation.request.SendNotificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Notification", description = "사용자 알림 API")
@RequestMapping("/notification")
public interface NotificationApi {
    @Operation(
            summary = "[Test Only] Send notification API",
            description = """
                     ## ✨ 주요 기능
                     
                     사용자 알림을 생성합니다.
                     
                     생성된 알림은 메시지브로커를 통해 ws 전역에 전파됩니다.
                     
                     해당 API는 테스트 전용이므로, 알림 관련 기타 기능 구현이 완료될 시 삭제됩니다.
                     """
    )
    @PostMapping("/send")
    ResponseEntity<Void> sendNotification(@Valid @RequestBody SendNotificationRequest request);
}
